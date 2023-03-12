package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import jp.glory.influxdb.practice.base.spring.config.InfluxDBProperty
import jp.glory.influxdb.practice.iostat.query.model.*
import jp.glory.influxdb.practice.iostat.query.service.IOStatStatisticsService
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class IOStatStatisticsServiceImpl(
    influxDBProperty: InfluxDBProperty,
    influxDBClient: InfluxDBClient
) : IOStatStatisticsService {
    private val cpu = CpuMeasurement(
        bucketName = influxDBProperty.bucket,
        influxDBClient = influxDBClient
    )
    private val disk = DiskMeasurement(
        bucketName = influxDBProperty.bucket,
        influxDBClient = influxDBClient
    )

    override fun aggregateStatistics(
        latest: Int,
        interval: Int
    ): IOStatStatistics {
        val cpuStatistics = cpu.aggregateCpuResult(
            latest = latest,
            interval = interval
        )
            .let { toCpuStatistics(it) }
        val diskStatistics = disk.aggregateCpuResult(
            latest = latest,
            interval = interval
        )
            .let { toDiskStatistics(it) }

        return IOStatStatistics(
            startedAt = cpuStatistics.startedAt,
            endedAt = cpuStatistics.endedAt,
            cpuStatistics = cpuStatistics,
            diskStatistics = diskStatistics
        )
    }

    private fun toCpuStatistics(
        results: CpuMeasurement.CpuStatisticsResults
    ): CPUStatistics = CPUStatistics(
        startedAt = results.startedAt,
        endedAt = results.endedAt,
        user = toStatisticsRows(results.user),
        system = toStatisticsRows(results.system),
        idle = toStatisticsRows(results.idle),
        per1Minutes = toStatisticsRows(results.per1Minutes),
        per5Minutes = toStatisticsRows(results.per5Minutes),
        per15Minutes = toStatisticsRows(results.per15Minutes),
    )

    private fun toDiskStatistics(
        results: DiskMeasurement.PerformanceStatisticsResults
    ): DiskStatistics = DiskStatistics(
        startedAt = results.startedAt,
        endedAt =  results.endedAt,
        bytesPerTransfer = toTotalableStatisticsRows(results.bytesPerTransfer),
        transfersPerSecond = toTotalableStatisticsRows(results.transfersPerSecond),
        bytesPerSecond = toTotalableStatisticsRows(results.bytesPerSecond)
    )

    private fun <T> toStatisticsRows(
        results: List<CpuMeasurement.CpuStatisticsResult<T>>
    ): StatisticsRows<T> {
        var startedAt = OffsetDateTime.MAX
        var endedAt = OffsetDateTime.MIN
        return results.map {
            StatisticsRow(
                recordAt = it.recordAt,
                min = it.min,
                max = it.max,
                average = it.average
            )
                .also { row ->
                    startedAt = listOf(startedAt, row.recordAt).max()
                    endedAt = listOf(endedAt,  row.recordAt).min()
                }
        }
            .let {
                StatisticsRows(
                    startedAt = startedAt,
                    endedAt = endedAt,
                    rows = it
                )
            }
    }

    private fun <T> toTotalableStatisticsRows(
        results: List<DiskMeasurement.PerformanceStatisticsResult<T>>
    ): TotalAbleStatisticsRows<T> {
        var startedAt = OffsetDateTime.MAX
        var endedAt = OffsetDateTime.MIN
        return results.map {
            TotalAbleStatisticsRow(
                recordAt = it.recordAt,
                min = it.min,
                max = it.max,
                average = it.average,
                total = it.total
            )
                .also { row ->
                    startedAt = listOf(startedAt, row.recordAt).max()
                    endedAt = listOf(endedAt,  row.recordAt).min()
                }
        }
            .let {
                TotalAbleStatisticsRows(
                    startedAt = startedAt,
                    endedAt = endedAt,
                    rows = it
                )
            }
    }
}