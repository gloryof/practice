package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import com.influxdb.query.FluxRecord
import jp.glory.influxdb.practice.iostat.domain.model.CPUStat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.TreeMap

class CpuMeasurement(
    private val bucketName: String,
    private val influxDBClient: InfluxDBClient
) {
    companion object {
        const val pointName = "cpu"
    }
    private enum class Fields(
        val filedName: String
    ) {
        User("user"),
        System("system"),
        Idle("idle"),
        Per1Minute("per1minutes"),
        Per5Minute("per5minutes"),
        Per15Minute("per15minutes");

        companion object {
            fun fromValue(value: String?): Fields {
                return Fields.values().first { it.filedName == value }
            }
        }
    }

    fun writeResult(
        recordAt: OffsetDateTime,
        cpuStat: CPUStat,
    ) {

        influxDBClient.writeApiBlocking
            .let {
                it.writePoint(
                    Point.measurement(pointName)
                        .time(recordAt.toInstant().toEpochMilli(), WritePrecision.MS)
                        .addTag("type", "usage")
                        .addField(Fields.User.filedName, cpuStat.user)
                        .addField(Fields.System.filedName, cpuStat.system)
                        .addField(Fields.Idle.filedName, cpuStat.idle)
                )
            }

        influxDBClient.writeApiBlocking
            .let {
                it.writePoint(
                    Point.measurement(pointName)
                        .time(recordAt.toInstant().toEpochMilli(), WritePrecision.MS)
                        .addTag("type", "load-average")
                        .addField(Fields.Per1Minute.filedName, cpuStat.loadAverage.per1Minutes)
                        .addField(Fields.Per5Minute.filedName, cpuStat.loadAverage.per5Minutes)
                        .addField(Fields.Per15Minute.filedName, cpuStat.loadAverage.per15Minutes)
                )
            }
    }

    fun findUsageCpuResult(value: Int): List<CpuResult> {
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
                        Fields.User -> tempResult.user = record.value as Long
                        Fields.System -> tempResult.system = record.value as Long
                        Fields.Idle -> tempResult.idle = record.value as Long
                        Fields.Per1Minute -> tempResult.per1Minutes = record.value as Double
                        Fields.Per5Minute -> tempResult.per5Minutes = record.value as Double
                        Fields.Per15Minute -> tempResult.per15Minutes = record.value as Double
                    }
                }
            }

        return tempResults.map { (_, it) -> CpuResult.create(it) }
            .toList()
    }

    fun aggregateCpuResult(
        latest: Int,
        interval: Int
    ): CpuStatisticsResults {
        val tempResult = TempCpuStatisticsResults()
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

        return tempResult.toFixedResult()
    }

    private fun createAggregateQuery(
        latest: Int,
        interval: Int,
        funcName: String
    ): String = """
        from(bucket: "$bucketName")
            |> range(start: -${latest}m)
            |> filter(fn: (r) => r._measurement == "$pointName")
            |> aggregateWindow(every: ${interval}m, fn: ${funcName})
            |> filter(fn: (r) => exists r._value)
    """.trimIndent()

    class CpuResult(
        val recordAt: OffsetDateTime,
        val user: Long,
        val system: Long,
        val idle: Long,
        val per1Minutes: Double,
        val per5Minutes: Double,
        val per15Minutes: Double
    ) {
        companion object {
            fun create(tempResult: TempResult) : CpuResult =
                CpuResult(
                    recordAt = requireNotNull(tempResult.recordAt),
                    user = requireNotNull(tempResult.user),
                    system = requireNotNull(tempResult.system),
                    idle = requireNotNull(tempResult.idle),
                    per1Minutes = requireNotNull(tempResult.per1Minutes),
                    per5Minutes = requireNotNull(tempResult.per5Minutes),
                    per15Minutes = requireNotNull(tempResult.per15Minutes)
                )
        }
    }

    class CpuStatisticsResults(
        val startedAt: OffsetDateTime,
        val endedAt: OffsetDateTime,
        val user: List<CpuStatisticsResult<Long>>,
        val system: List<CpuStatisticsResult<Long>>,
        val idle: List<CpuStatisticsResult<Long>>,
        val per1Minutes: List<CpuStatisticsResult<Double>>,
        val per5Minutes: List<CpuStatisticsResult<Double>>,
        val per15Minutes: List<CpuStatisticsResult<Double>>
    )
    class CpuStatisticsResult<T>(
        val recordAt: OffsetDateTime,
        val max: T,
        val min: T,
        val average: Double
    )

    class TempResult(
        var recordAt: OffsetDateTime? = null,
        var user: Long? = null,
        var system: Long? = null,
        var idle: Long? = null,
        var per1Minutes: Double? = null,
        var per5Minutes: Double? = null,
        var per15Minutes: Double? = null
    )

    class TempCpuStatisticsResults {
        private var startedAt: OffsetDateTime? = null
        private var endedAt: OffsetDateTime? = null

        private val user = TreeMap<OffsetDateTime, TempCpuStatisticsResult<Long>>()
        private val system = TreeMap<OffsetDateTime, TempCpuStatisticsResult<Long>>()
        private val idle = TreeMap<OffsetDateTime, TempCpuStatisticsResult<Long>>()
        private val per1Minutes = TreeMap<OffsetDateTime, TempCpuStatisticsResult<Double>>()
        private val per5Minutes = TreeMap<OffsetDateTime, TempCpuStatisticsResult<Double>>()
        private val per15Minutes = TreeMap<OffsetDateTime, TempCpuStatisticsResult<Double>>()

        private enum class FuncType {
            Min,
            Max,
            Average
        }

        fun toFixedResult(): CpuStatisticsResults = CpuStatisticsResults(
            startedAt = requireNotNull(startedAt),
            endedAt = requireNotNull(endedAt),
            user = user.values.map { it.toFixedResult() },
            system = system.values.map { it.toFixedResult() },
            idle = idle.values.map { it.toFixedResult() },
            per1Minutes = per1Minutes.values.map { it.toFixedResult() },
            per5Minutes = per5Minutes.values.map { it.toFixedResult() },
            per15Minutes = per15Minutes.values.map { it.toFixedResult() }
        )

        fun updateMin(record: FluxRecord) {
            val funcType = FuncType.Min
            when(Fields.fromValue(record.field)) {
                Fields.User -> update(record, user, funcType)
                Fields.System -> update(record, system, funcType)
                Fields.Idle -> update(record, idle, funcType)
                Fields.Per1Minute -> update(record, per1Minutes, funcType)
                Fields.Per5Minute -> update(record, per5Minutes, funcType)
                Fields.Per15Minute -> update(record, per15Minutes, funcType)
            }
        }

        fun updateMax(record: FluxRecord) {
            val funcType = FuncType.Max
            when(Fields.fromValue(record.field)) {
                Fields.User -> update(record, user, funcType)
                Fields.System -> update(record, system, funcType)
                Fields.Idle -> update(record, idle, funcType)
                Fields.Per1Minute -> update(record, per1Minutes, funcType)
                Fields.Per5Minute -> update(record, per5Minutes, funcType)
                Fields.Per15Minute -> update(record, per15Minutes, funcType)
            }
        }

        fun updateAverage(record: FluxRecord) {
            val funcType = FuncType.Average
            when(Fields.fromValue(record.field)) {
                Fields.User -> update(record, user, funcType)
                Fields.System -> update(record, system, funcType)
                Fields.Idle -> update(record, idle, funcType)
                Fields.Per1Minute -> update(record, per1Minutes, funcType)
                Fields.Per5Minute -> update(record, per5Minutes, funcType)
                Fields.Per15Minute -> update(record, per15Minutes, funcType)
            }
        }

        private fun <T> update(
            record: FluxRecord,
            target: TreeMap<OffsetDateTime, TempCpuStatisticsResult<T>>,
            funcType: FuncType
        ) {
            val time = OffsetDateTime.ofInstant(record.time, ZoneId.of("UTC"))
            updateStartedAt(time)
            updateEndedAt(time)
            val tempResult = target.computeIfAbsent(time) {
                TempCpuStatisticsResult(recordAt = time)
            }
            when(funcType) {
                FuncType.Min -> tempResult.min = record.value as T
                FuncType.Max -> tempResult.max = record.value as T
                FuncType.Average -> tempResult.average = record.value as Double
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
    class TempCpuStatisticsResult<T>(
        val recordAt: OffsetDateTime,
        var max: T? = null,
        var min: T? = null,
        var average: Double? = null
    ) {
        fun toFixedResult(): CpuStatisticsResult<T> = CpuStatisticsResult(
            recordAt = recordAt,
            max = requireNotNull(max),
            min = requireNotNull(min),
            average = requireNotNull(average)
        )
    }
}