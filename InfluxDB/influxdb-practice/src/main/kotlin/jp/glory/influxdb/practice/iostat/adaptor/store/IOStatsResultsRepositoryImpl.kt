package jp.glory.influxdb.practice.iostat.adaptor.store

import jp.glory.influxdb.practice.iostat.domain.model.IOStatsResults
import jp.glory.influxdb.practice.iostat.domain.repository.IOStatsResultsRepository
import org.springframework.stereotype.Repository

@Repository
class IOStatsResultsRepositoryImpl : IOStatsResultsRepository {
    private val records = mutableListOf<IOStatsResults>()
    override fun register(results: IOStatsResults) {
        records.add(results)
    }

    override fun findAll(): List<IOStatsResults> = records
}