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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
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
    fun import(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Any> {
        BufferedReader(InputStreamReader(file.inputStream))
            .readLines()
            .let { importUseCase.importStats(it) }

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