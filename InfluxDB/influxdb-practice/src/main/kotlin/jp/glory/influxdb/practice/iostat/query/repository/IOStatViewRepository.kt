package jp.glory.influxdb.practice.iostat.query.repository

import jp.glory.influxdb.practice.iostat.query.model.IOStatView

interface IOStatViewRepository {
    fun findLatest(minutes: Int): IOStatView
}