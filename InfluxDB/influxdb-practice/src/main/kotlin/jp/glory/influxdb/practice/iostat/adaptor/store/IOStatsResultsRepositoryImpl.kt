package jp.glory.influxdb.practice.iostat.adaptor.store

import com.influxdb.client.InfluxDBClient
import jp.glory.influxdb.practice.base.spring.config.InfluxDBProperty
import jp.glory.influxdb.practice.iostat.domain.model.IOStatsResults
import jp.glory.influxdb.practice.iostat.domain.repository.IOStatsResultsRepository
import org.springframework.stereotype.Repository

@Repository
class IOStatsResultsRepositoryImpl(
    private val influxDBProperty: InfluxDBProperty,
    private val influxDBClient: InfluxDBClient
) : IOStatsResultsRepository {
    private val cpu = CpuMeasurement(
        bucketName = influxDBProperty.bucket,
        influxDBClient = influxDBClient
    )
    private val disk = DiskMeasurement(
        bucketName = influxDBProperty.bucket,
        influxDBClient = influxDBClient
    )
    override fun register(results: IOStatsResults) {
        results.results.forEach {
            cpu.writeResult(
                recordAt = it.recordAt,
                cpuStat = it.cpuStat
            )
            disk.writeResult(
                recordAt = it.recordAt,
                diskStat = it.diskStat
            )
        }
    }

}