package jp.glory.influxdb.practice.iostat.use_case

import jp.glory.influxdb.practice.base.use_case.UseCase
import jp.glory.influxdb.practice.iostat.domain.model.IOStatsResult
import jp.glory.influxdb.practice.iostat.domain.repository.IOStatsResultsRepository
import java.time.OffsetDateTime

@UseCase
class FindIOStatsUseCase(
    private val repository: IOStatsResultsRepository
) {
    fun findAllRows(): Output =
        repository.findAll()
            .flatMap {
                it.results.map { row -> Row(row) }
            }
            .let { Output(it) }

    class Output(
        val rows: List<Row>
    )

    class Row private constructor(
        val recordAt: OffsetDateTime,
        val bytesPerTransfer: Long,
        val transfersPerSecond: Int,
        val bytesPerSecond: Long,
        val user: Int,
        val system: Int,
        val idle: Int,
        val per1Minutes: Double,
        val per5Minutes: Double,
        val per15Minutes: Double,
    ) {
        constructor(result: IOStatsResult) : this(
            recordAt = result.recordAt,
            bytesPerTransfer = result.diskStat.bytesPerTransfer,
            transfersPerSecond = result.diskStat.transfersPerSecond,
            bytesPerSecond = result.diskStat.bytesPerSecond,
            user = result.cpuStat.user,
            system = result.cpuStat.system,
            idle = result.cpuStat.idle,
            per1Minutes = result.cpuStat.loadAverage.per1Minutes,
            per5Minutes = result.cpuStat.loadAverage.per5Minutes,
            per15Minutes = result.cpuStat.loadAverage.per15Minutes,
        )
    }
}
