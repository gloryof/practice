package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.Query
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import jp.glory.influxdb.practice.iostat.domain.model.CPUStat
import java.time.OffsetDateTime
import java.time.ZoneId

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
                    recordAt = tempResult.recordAt,
                    user = requireNotNull(tempResult.user),
                    system = requireNotNull(tempResult.system),
                    idle = requireNotNull(tempResult.idle),
                    per1Minutes = requireNotNull(tempResult.per1Minutes),
                    per5Minutes = requireNotNull(tempResult.per5Minutes),
                    per15Minutes = requireNotNull(tempResult.per15Minutes)
                )
        }
    }

    inner class TempResult(
        val recordAt: OffsetDateTime,
        var user: Long? = null,
        var system: Long? = null,
        var idle: Long? = null,
        var per1Minutes: Double? = null,
        var per5Minutes: Double? = null,
        var per15Minutes: Double? = null
    )
}