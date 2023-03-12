package jp.glory.influxdb.practice.iostat.query.service

import jp.glory.influxdb.practice.iostat.query.model.IOStatStatistics

interface IOStatStatisticsService {
    // 本来、モデル/サービスとして表現すべきだが、
    // InfluxDBの機能を使いたいのでInterfaceの定義のみ
    fun aggregateStatistics(
        latest: Int,
        interval: Int
    ): IOStatStatistics
}