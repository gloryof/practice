package jp.glory.influxdb.practice.iostat.domain.repository

import jp.glory.influxdb.practice.iostat.domain.model.IOStatsResults

interface IOStatsResultsRepository {
    fun register(results: IOStatsResults)
}