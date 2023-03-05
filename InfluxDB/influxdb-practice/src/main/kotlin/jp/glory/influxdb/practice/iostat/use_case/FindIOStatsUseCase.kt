package jp.glory.influxdb.practice.iostat.use_case

import jp.glory.influxdb.practice.base.use_case.UseCase
import jp.glory.influxdb.practice.iostat.query.model.IOStatView
import jp.glory.influxdb.practice.iostat.query.model.ViewRow

import jp.glory.influxdb.practice.iostat.query.repository.IOStatViewRepository
import java.time.OffsetDateTime

@UseCase
class FindIOStatsUseCase(
    private val repository: IOStatViewRepository
) {
    fun findLatest(
        minutes: Int
    ): Output =
        repository.findLatest(minutes)
            .let { Output(it) }

    class Output private constructor(
        val user: List<Row<Long>>,
        val system: List<Row<Long>>,
        val idle: List<Row<Long>>,
        val per1Minutes: List<Row<Double>>,
        val per5Minutes: List<Row<Double>>,
        val per15Minutes: List<Row<Double>>,
        val bytesPerTransfer: List<Row<Long>>,
        val transfersPerSecond: List<Row<Long>>,
        val bytesPerSecond: List<Row<Long>>
    ) {
        constructor(view: IOStatView) : this(
            user = view.cpuView.user.viewRows.map { Row(it) },
            system = view.cpuView.system.viewRows.map { Row(it) },
            idle = view.cpuView.idle.viewRows.map { Row(it) },
            per1Minutes = view.cpuView.per1Minutes.viewRows.map { Row(it) },
            per5Minutes = view.cpuView.per5Minutes.viewRows.map { Row(it) },
            per15Minutes = view.cpuView.per15Minutes.viewRows.map { Row(it) },
            bytesPerTransfer = view.diskView.bytesPerTransfer.viewRows.map { Row(it) },
            transfersPerSecond = view.diskView.transfersPerSecond.viewRows.map { Row(it) },
            bytesPerSecond = view.diskView.bytesPerSecond.viewRows.map { Row(it) },
        )
    }

    class Row<T> private constructor(
        val recordAt: OffsetDateTime,
        val value: T
    ) {
        constructor(result: ViewRow<T>) : this(
            recordAt = result.recordAt,
            value = result.value
        )
    }
}
