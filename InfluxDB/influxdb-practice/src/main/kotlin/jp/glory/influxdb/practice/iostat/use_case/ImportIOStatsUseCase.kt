package jp.glory.influxdb.practice.iostat.use_case

import jp.glory.influxdb.practice.base.use_case.UseCase
import jp.glory.influxdb.practice.iostat.domain.model.IOStatsImportData
import jp.glory.influxdb.practice.iostat.domain.repository.IOStatsResultsRepository
import java.time.Clock

@UseCase
class ImportIOStatsUseCase(
    private val repository: IOStatsResultsRepository,
    private val clock: Clock
) {
    fun importStats(rows: List<String>) {
        IOStatsImportData(rows)
            .createEvent(clock)
            .let { repository.register(it) }
    }
}