package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import com.influxdb.query.FluxRecord
import jp.glory.influxdb.practice.iostat.domain.model.DiskStat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

class DiskMeasurement(
    private val bucketName: String,
    private val influxDBClient: InfluxDBClient
) {
    companion object {
        const val pointName = "disk"
    }
    private enum class Fields(
        val filedName: String
    ) {
        BytesPerTransfer("bytesPerTransfer"),
        TransfersPerSecond("transfersPerSecond"),
        BytesPerSecond("bytesPerSecond");

        companion object {
            fun fromValue(value: String?): Fields {
                return Fields.values().first { it.filedName == value }
            }
        }
    }

    fun writeResult(
        recordAt: OffsetDateTime,
        diskStat: DiskStat
    ) {

        influxDBClient.writeApiBlocking
            .let {
                it.writePoint(
                    Point.measurement(pointName)
                        .time(recordAt.toInstant().toEpochMilli(), WritePrecision.MS)
                        .addTag("type", "performance")
                        .addField(Fields.BytesPerTransfer.filedName, diskStat.bytesPerTransfer)
                        .addField(Fields.TransfersPerSecond.filedName, diskStat.transfersPerSecond)
                        .addField(Fields.BytesPerSecond.filedName, diskStat.bytesPerSecond)
                )
            }

    }

    fun findPerformanceLatest(value: Int): List<PerformanceResult> {
        val query = """
            from(bucket: "$bucketName")
                |> range(start: -${value}m)
                |> filter(fn: (r) => r._measurement == "$pointName")
        """.trimIndent()
        val tempResults = mutableMapOf<OffsetDateTime, TempResult>()
        influxDBClient.queryApi.query(query)
            .forEach { table ->
                table.records.forEach { record ->
                    val time = OffsetDateTime.ofInstant(record.time, ZoneId.of("UTC"))
                    val tempResult = tempResults.computeIfAbsent(
                        time
                    ) { key -> TempResult(key) }
                    when(Fields.fromValue(record.field)) {
                        Fields.BytesPerTransfer -> tempResult.bytesPerTransfer = record.value as Long
                        Fields.TransfersPerSecond -> tempResult.transfersPerSecond = record.value as Long
                        Fields.BytesPerSecond -> tempResult.bytesPerSecond = record.value as Long
                    }
                }
            }

        return tempResults.map { (_, it) -> PerformanceResult.create(it) }
            .toList()
    }

    fun aggregateCpuResult(
        latest: Int,
        interval: Int
    ): PerformanceStatisticsResults {
        val tempResult = TempPerformanceStatisticsResults()
        influxDBClient.queryApi.query(
            createAggregateQuery(
                latest = latest,
                interval = interval,
                funcName = "min"
            )
        )
            .forEach { table ->
                table.records.forEach { tempResult.updateMin(it) }
            }

        influxDBClient.queryApi.query(
            createAggregateQuery(
                latest = latest,
                interval = interval,
                funcName = "max"
            )
        )
            .forEach { table ->
                table.records.forEach { tempResult.updateMax(it) }
            }

        influxDBClient.queryApi.query(
            createAggregateQuery(
                latest = latest,
                interval = interval,
                funcName = "mean"
            )
        )
            .forEach { table ->
                table.records.forEach { tempResult.updateAverage(it) }
            }

        influxDBClient.queryApi.query(
            createAggregateQuery(
                latest = latest,
                interval = interval,
                funcName = "sum"
            )
        )
            .forEach { table ->
                table.records.forEach { tempResult.updateTotal(it) }
            }

        return tempResult.toFixedResult()
    }

    private fun createAggregateQuery(
        latest: Int,
        interval: Int,
        funcName: String
    ): String = """
        from(bucket: "$bucketName")
            |> range(start: -${latest}m)
            |> filter(fn: (r) => r._measurement == "${pointName}")
            |> aggregateWindow(every: ${interval}m, fn: ${funcName})
            |> filter(fn: (r) => exists r._value)
    """.trimIndent()

    class PerformanceResult(
        val recordAt: OffsetDateTime,
        val bytesPerTransfer: Long,
        val transfersPerSecond: Long,
        val bytesPerSecond: Long
    ) {
        companion object {
            fun create(tempResult: TempResult) : PerformanceResult =
                PerformanceResult(
                    recordAt = tempResult.recordAt,
                    bytesPerTransfer = requireNotNull(tempResult.bytesPerTransfer),
                    transfersPerSecond = requireNotNull(tempResult.transfersPerSecond),
                    bytesPerSecond = requireNotNull(tempResult.bytesPerSecond),
                )
        }
    }

    class PerformanceStatisticsResults(
        val startedAt: OffsetDateTime,
        val endedAt: OffsetDateTime,
        val bytesPerTransfer: List<PerformanceStatisticsResult<Long>>,
        val transfersPerSecond: List<PerformanceStatisticsResult<Long>>,
        val bytesPerSecond: List<PerformanceStatisticsResult<Long>>,

        )
    class PerformanceStatisticsResult<T>(
        val recordAt: OffsetDateTime,
        var max: T,
        var min: T,
        var average: Double,
        var total: T
    )
    inner class TempResult(
        val recordAt: OffsetDateTime,
        var bytesPerTransfer: Long? = null,
        var transfersPerSecond: Long? = null,
        var bytesPerSecond: Long? = null
    )
    class TempPerformanceStatisticsResults {
        private var startedAt: OffsetDateTime? = null
        private var endedAt: OffsetDateTime? = null
        private val bytesPerTransfer = TreeMap<OffsetDateTime, TempPerformanceStatisticsResult<Long>>()
        private val transfersPerSecond = TreeMap<OffsetDateTime, TempPerformanceStatisticsResult<Long>>()
        private val bytesPerSecond = TreeMap<OffsetDateTime, TempPerformanceStatisticsResult<Long>>()

        private enum class FuncType {
            Min,
            Max,
            Average,
            Total
        }

        fun toFixedResult(): PerformanceStatisticsResults = PerformanceStatisticsResults(
            startedAt = requireNotNull(startedAt),
            endedAt = requireNotNull(endedAt),
            bytesPerTransfer = bytesPerTransfer.values.map { it.toFixedResult() },
            transfersPerSecond = transfersPerSecond.values.map { it.toFixedResult() },
            bytesPerSecond = bytesPerSecond.values.map { it.toFixedResult() },
        )

        fun updateMin(record: FluxRecord) {
            val funcType = FuncType.Min
            when(Fields.fromValue(record.field)) {
                Fields.BytesPerTransfer -> update(record, bytesPerTransfer, funcType)
                Fields.TransfersPerSecond -> update(record, transfersPerSecond, funcType)
                Fields.BytesPerSecond -> update(record, bytesPerSecond, funcType)
            }
        }

        fun updateMax(record: FluxRecord) {
            val funcType = FuncType.Max
            when(Fields.fromValue(record.field)) {
                Fields.BytesPerTransfer -> update(record, bytesPerTransfer, funcType)
                Fields.TransfersPerSecond -> update(record, transfersPerSecond, funcType)
                Fields.BytesPerSecond -> update(record, bytesPerSecond, funcType)
            }
        }

        fun updateAverage(record: FluxRecord) {
            val funcType = FuncType.Average
            when(Fields.fromValue(record.field)) {
                Fields.BytesPerTransfer -> update(record, bytesPerTransfer, funcType)
                Fields.TransfersPerSecond -> update(record, transfersPerSecond, funcType)
                Fields.BytesPerSecond -> update(record, bytesPerSecond, funcType)
            }
        }
        fun updateTotal(record: FluxRecord) {
            val funcType = FuncType.Total
            when(Fields.fromValue(record.field)) {
                Fields.BytesPerTransfer -> update(record, bytesPerTransfer, funcType)
                Fields.TransfersPerSecond -> update(record, transfersPerSecond, funcType)
                Fields.BytesPerSecond -> update(record, bytesPerSecond, funcType)
            }
        }

        private fun <T> update(
            record: FluxRecord,
            target: TreeMap<OffsetDateTime, TempPerformanceStatisticsResult<T>>,
            funcType: FuncType
        ) {
            val time = OffsetDateTime.ofInstant(record.time, ZoneId.of("UTC"))
            updateStartedAt(time)
            updateEndedAt(time)
            val tempResult = target.computeIfAbsent(time) {
                TempPerformanceStatisticsResult(recordAt = time)
            }
            when(funcType) {
                FuncType.Min -> tempResult.min = record.value as T
                FuncType.Max -> tempResult.max = record.value as T
                FuncType.Average -> tempResult.average = record.value as Double
                FuncType.Total -> tempResult.total = record.value as T
            }
        }

        private fun updateStartedAt(
            value: OffsetDateTime
        ) {
            if (startedAt == null) {
                startedAt = value
                return
            }

            if (startedAt!!.isAfter(value)) {
                startedAt = value
            }
        }

        private fun updateEndedAt(
            value: OffsetDateTime
        ) {
            if (endedAt == null) {
                endedAt = value
                return
            }

            if (endedAt!!.isBefore(value)) {
                endedAt = value
            }
        }
    }
    class TempPerformanceStatisticsResult<T>(
        val recordAt: OffsetDateTime,
        var max: T? = null,
        var min: T? = null,
        var average: Double? = null,
        var total: T? = null
    ) {
        fun toFixedResult(): PerformanceStatisticsResult<T> = PerformanceStatisticsResult(
            recordAt = recordAt,
            max = requireNotNull(max),
            min = requireNotNull(min),
            average = requireNotNull(average),
            total = requireNotNull(total)
        )
    }
}