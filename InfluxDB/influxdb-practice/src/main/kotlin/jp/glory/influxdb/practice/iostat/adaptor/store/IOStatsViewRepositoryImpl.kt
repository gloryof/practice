package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import jp.glory.influxdb.practice.base.spring.config.InfluxDBProperty
import jp.glory.influxdb.practice.iostat.query.model.*
import jp.glory.influxdb.practice.iostat.query.repository.IOStatViewRepository
import org.springframework.stereotype.Repository

@Repository
class IOStatsViewRepositoryImpl(
    influxDBProperty: InfluxDBProperty,
    influxDBClient: InfluxDBClient
) : IOStatViewRepository {
    private val cpu = CpuMeasurement(
        bucketName = influxDBProperty.bucket,
        influxDBClient = influxDBClient
    )
    private val disk = DiskMeasurement(
        bucketName = influxDBProperty.bucket,
        influxDBClient = influxDBClient
    )
    override fun findLatest(minutes: Int): IOStatView =
        IOStatView(
            cpuView = findCPUViewLatest(minutes),
            diskView = findDiskViewLatest(minutes)
        )


    private fun findCPUViewLatest(minutes: Int): CPUView =
        cpu.findUsageCpuResult(minutes)
            .let { toCPUView(it) }

    private fun findDiskViewLatest(minutes: Int): DiskView =
        disk
            .findPerformanceLatest(minutes)
            .let { toDiskFlatResult(it) }
            .let {
                DiskView(
                    bytesPerTransfer = it.bytesPerTransfer,
                    transfersPerSecond = it.transfersPerSecond,
                    bytesPerSecond = it.bytesPerSecond
                )
            }

    private fun toCPUView(
        results: List<CpuMeasurement.CpuResult>
    ): CPUView {
        val user = mutableListOf<ViewRow<Long>>()
        val system = mutableListOf<ViewRow<Long>>()
        val idle = mutableListOf<ViewRow<Long>>()
        val per1Minutes = mutableListOf<ViewRow<Double>>()
        val per5Minutes = mutableListOf<ViewRow<Double>>()
        val per15Minutes = mutableListOf<ViewRow<Double>>()

        results.forEach {
            user.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.user
                )
            )
            system.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.system
                )
            )
            idle.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.idle
                )
            )
            per1Minutes.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.per1Minutes
                )
            )
            per5Minutes.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.per5Minutes
                )
            )
            per15Minutes.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.per15Minutes
                )
            )
        }

        return CPUView(
            user = ViewRows(user.toList()),
            system = ViewRows(system.toList()),
            idle = ViewRows(idle.toList()),
            per1Minutes = ViewRows(per1Minutes.toList()),
            per5Minutes = ViewRows(per5Minutes.toList()),
            per15Minutes = ViewRows(per15Minutes.toList()),
        )
    }

    private fun toDiskFlatResult(
        results: List<DiskMeasurement.PerformanceResult>
    ): DiskFlatResult {
        val bytesPerTransfer = mutableListOf<ViewRow<Long>>()
        val transfersPerSecond = mutableListOf<ViewRow<Long>>()
        val bytesPerSecond = mutableListOf<ViewRow<Long>>()

        results.forEach {
            bytesPerTransfer.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.bytesPerTransfer
                )
            )
            transfersPerSecond.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.transfersPerSecond
                )
            )
            bytesPerSecond.add(
                ViewRow(
                    recordAt = it.recordAt,
                    value = it.bytesPerSecond
                )
            )
        }

        return DiskFlatResult(
            bytesPerTransfer = ViewRows(bytesPerTransfer.toList()),
            transfersPerSecond = ViewRows(transfersPerSecond.toList()),
            bytesPerSecond = ViewRows(bytesPerSecond.toList()),
        )
    }

    private class DiskFlatResult(
        val bytesPerTransfer: ViewRows<Long>,
        val transfersPerSecond: ViewRows<Long>,
        val bytesPerSecond: ViewRows<Long>
    )
}