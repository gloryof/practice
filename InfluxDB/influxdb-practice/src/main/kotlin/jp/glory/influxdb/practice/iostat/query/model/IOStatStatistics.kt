package jp.glory.influxdb.practice.iostat.query.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.OffsetDateTime

class IOStatStatistics(
    val startedAt: OffsetDateTime,
    val endedAt: OffsetDateTime,
    val cpuStatistics: CPUStatistics,
    val diskStatistics: DiskStatistics
)

class CPUStatistics(
    val startedAt: OffsetDateTime,
    val endedAt: OffsetDateTime,
    val user: StatisticsRows<Long>,
    val system: StatisticsRows<Long>,
    val idle: StatisticsRows<Long>,
    val per1Minutes: StatisticsRows<Double>,
    val per5Minutes: StatisticsRows<Double>,
    val per15Minutes: StatisticsRows<Double>
)

class DiskStatistics(
    val startedAt: OffsetDateTime,
    val endedAt: OffsetDateTime,
    val bytesPerTransfer: TotalAbleStatisticsRows<Long>,
    val transfersPerSecond: TotalAbleStatisticsRows<Long>,
    val bytesPerSecond: TotalAbleStatisticsRows<Long>
)

class StatisticsRows<T>(
    val startedAt: OffsetDateTime,
    val endedAt: OffsetDateTime,
    val rows: List<StatisticsRow<T>>
)

class TotalAbleStatisticsRows<T>(
    val startedAt: OffsetDateTime,
    val endedAt: OffsetDateTime,
    val rows: List<TotalAbleStatisticsRow<T>>
)

class StatisticsRow<T>(
    val recordAt: OffsetDateTime,
    private val average: Double,
    val max: T,
    val min: T
) {
    fun getAverage(): Double =
        BigDecimal(average)
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()
}

class TotalAbleStatisticsRow<T>(
    val recordAt: OffsetDateTime,
    val total: T,
    private val average: Double,
    val max: T,
    val min: T
) {
    fun getAverage(): Double =
        BigDecimal(average)
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()
}