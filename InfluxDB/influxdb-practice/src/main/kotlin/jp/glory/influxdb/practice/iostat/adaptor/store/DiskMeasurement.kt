package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import jp.glory.influxdb.practice.iostat.domain.model.DiskStat
import java.time.OffsetDateTime
import java.time.ZoneId

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
    inner class TempResult(
        val recordAt: OffsetDateTime,
        var bytesPerTransfer: Long? = null,
        var transfersPerSecond: Long? = null,
        var bytesPerSecond: Long? = null
    )
}