package jp.glory.influxdb.practice.iostat.use_case

import jp.glory.influxdb.practice.base.use_case.UseCase
import jp.glory.influxdb.practice.iostat.query.model.*

import jp.glory.influxdb.practice.iostat.query.repository.IOStatViewRepository
import jp.glory.influxdb.practice.iostat.query.service.IOStatStatisticsService
import java.time.OffsetDateTime

@UseCase
class FindIOStatsUseCase(
    private val repository: IOStatViewRepository,
    private val service: IOStatStatisticsService
) {
    fun findLatest(
        minutes: Int
    ): LatestOutput =
        repository.findLatest(minutes)
            .let { LatestOutput(it) }

    fun findStatistics(
        latest: Int,
        interval: Int
    ): StatisticsOutput =
        service.aggregateStatistics(
            latest = latest,
            interval = interval
        )
            .let { StatisticsOutput(it) }

    class LatestOutput private constructor(
        val startedAt: OffsetDateTime,
        val endedAt: OffsetDateTime,
        val user: List<ViewOutputDetail<Long>>,
        val system: List<ViewOutputDetail<Long>>,
        val idle: List<ViewOutputDetail<Long>>,
        val per1Minutes: List<ViewOutputDetail<Double>>,
        val per5Minutes: List<ViewOutputDetail<Double>>,
        val per15Minutes: List<ViewOutputDetail<Double>>,
        val bytesPerTransfer: List<ViewOutputDetail<Long>>,
        val transfersPerSecond: List<ViewOutputDetail<Long>>,
        val bytesPerSecond: List<ViewOutputDetail<Long>>
    ) {
        constructor(view: IOStatView) : this(
            startedAt = view.startedAt,
            endedAt = view.endedAt,
            user = view.cpuView.user.viewRows.map { ViewOutputDetail(it) },
            system = view.cpuView.system.viewRows.map { ViewOutputDetail(it) },
            idle = view.cpuView.idle.viewRows.map { ViewOutputDetail(it) },
            per1Minutes = view.cpuView.per1Minutes.viewRows.map { ViewOutputDetail(it) },
            per5Minutes = view.cpuView.per5Minutes.viewRows.map { ViewOutputDetail(it) },
            per15Minutes = view.cpuView.per15Minutes.viewRows.map { ViewOutputDetail(it) },
            bytesPerTransfer = view.diskView.bytesPerTransfer.viewRows.map { ViewOutputDetail(it) },
            transfersPerSecond = view.diskView.transfersPerSecond.viewRows.map { ViewOutputDetail(it) },
            bytesPerSecond = view.diskView.bytesPerSecond.viewRows.map { ViewOutputDetail(it) },
        )
    }

    class ViewOutputDetail<T> private constructor(
        val recordAt: OffsetDateTime,
        val value: T
    ) {
        constructor(result: ViewRow<T>) : this(
            recordAt = result.recordAt,
            value = result.value
        )
    }

    class StatisticsOutput private constructor(
        val startedAt: OffsetDateTime,
        val endedAt: OffsetDateTime,
        val user: List<StatisticsOutputDetail<Long>>,
        val system: List<StatisticsOutputDetail<Long>>,
        val idle: List<StatisticsOutputDetail<Long>>,
        val per1Minutes: List<StatisticsOutputDetail<Double>>,
        val per5Minutes: List<StatisticsOutputDetail<Double>>,
        val per15Minutes: List<StatisticsOutputDetail<Double>>,
        val bytesPerTransfer: List<StatisticsOutputTotalableDetail<Long>>,
        val transfersPerSecond: List<StatisticsOutputTotalableDetail<Long>>,
        val bytesPerSecond: List<StatisticsOutputTotalableDetail<Long>>
    ) {
        constructor(statistics: IOStatStatistics) : this(
            startedAt = statistics.startedAt,
            endedAt = statistics.endedAt,
            user = statistics.cpuStatistics.user.rows.map { StatisticsOutputDetail(it) },
            system = statistics.cpuStatistics.system.rows.map { StatisticsOutputDetail(it) },
            idle = statistics.cpuStatistics.idle.rows.map { StatisticsOutputDetail(it) },
            per1Minutes = statistics.cpuStatistics.per1Minutes.rows.map { StatisticsOutputDetail(it) },
            per5Minutes = statistics.cpuStatistics.per5Minutes.rows.map { StatisticsOutputDetail(it) },
            per15Minutes = statistics.cpuStatistics.per15Minutes.rows.map { StatisticsOutputDetail(it) },
            bytesPerTransfer = statistics.diskStatistics.bytesPerTransfer.rows
                .map { StatisticsOutputTotalableDetail(it) },
            transfersPerSecond = statistics.diskStatistics.transfersPerSecond.rows
                .map { StatisticsOutputTotalableDetail(it) },
            bytesPerSecond = statistics.diskStatistics.bytesPerSecond.rows
                .map { StatisticsOutputTotalableDetail(it) },
        )
    }

    class StatisticsOutputDetail<T> private constructor(
        val recordAt: OffsetDateTime,
        val average: Double,
        val max: T,
        val min: T
    ) {
        constructor(row: StatisticsRow<T>): this(
            recordAt = row.recordAt,
            average = row.getAverage(),
            max = row.max,
            min = row.min
        )
    }

    class StatisticsOutputTotalableDetail<T>(
        val recordAt: OffsetDateTime,
        val total: T,
        val average: Double,
        val max: T,
        val min: T,
    ) {

        constructor(row: TotalAbleStatisticsRow<T>): this(
            recordAt = row.recordAt,
            average = row.getAverage(),
            max = row.max,
            min = row.min,
            total = row.total
        )
    }
}
