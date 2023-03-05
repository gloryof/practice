package jp.glory.influxdb.practice.iostat.adaptor.store

import java.time.OffsetDateTime

class MeasurementResult<T> (
    val recordAt: OffsetDateTime,
    val value: T
)