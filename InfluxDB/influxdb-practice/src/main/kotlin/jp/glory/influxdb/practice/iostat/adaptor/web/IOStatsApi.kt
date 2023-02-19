package jp.glory.influxdb.practice.iostat.adaptor.web

import jp.glory.influxdb.practice.base.adaptor.web.EndpointConst
import jp.glory.influxdb.practice.base.adaptor.web.WebApi
import jp.glory.influxdb.practice.iostat.use_case.FindIOStatsUseCase
import jp.glory.influxdb.practice.iostat.use_case.ImportIOStatsUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.OffsetDateTime

@WebApi
@RequestMapping(EndpointConst.IOStats.IOStats)
class IOStatsApi(
    private val importUseCase: ImportIOStatsUseCase,
    private val findUseCase: FindIOStatsUseCase
) {
    @GetMapping
    fun getAll(): FindAllResponse =
        findUseCase.findAllRows()
            .let { it.rows.map { row -> RowResponse(row) } }
            .let { FindAllResponse(it) }

    @PostMapping
    fun import(): ResponseEntity<Any> {
        val stats = listOf(
            "KB/t,tps,MB/s,us,sy,id,1m,5m,15m",
            "32.11,8,0.24,6,2,92,3.57,3.36,3.35",
            "160.26,213,33.26,6,6,88,3.57,3.36,3.35",
            "10.59,209,2.16,9,7,84,3.57,3.36,3.35"
        )
        importUseCase
            .importStats(stats)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    class FindAllResponse(
        val rows: List<RowResponse>
    )

    class RowResponse private constructor(
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
        constructor(result: FindIOStatsUseCase.Row) : this(
            recordAt = result.recordAt,
            bytesPerTransfer = result.bytesPerTransfer,
            transfersPerSecond = result.transfersPerSecond,
            bytesPerSecond = result.bytesPerSecond,
            user = result.user,
            system = result.system,
            idle = result.idle,
            per1Minutes = result.per1Minutes,
            per5Minutes = result.per5Minutes,
            per15Minutes = result.per15Minutes,
        )
    }
}