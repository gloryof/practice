package jp.glory.influxdb.practice.iostat.adaptor.web

import jp.glory.influxdb.practice.base.adaptor.web.EndpointConst
import jp.glory.influxdb.practice.base.adaptor.web.WebApi
import jp.glory.influxdb.practice.iostat.use_case.FindIOStatsUseCase
import jp.glory.influxdb.practice.iostat.use_case.ImportIOStatsUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.OffsetDateTime

@WebApi
@RequestMapping(EndpointConst.IOStats.IOStats)
class IOStatsApi(
    private val importUseCase: ImportIOStatsUseCase,
    private val findUseCase: FindIOStatsUseCase
) {
    @GetMapping("/latest/{minute}")
    fun gatLatest(
        @PathVariable minute: Int
    ): FindAllResponse =
        findUseCase.findLatest(minute)
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
        val user: List<RowResponse<Long>>,
        val system: List<RowResponse<Long>>,
        val idle: List<RowResponse<Long>>,
        val per1Minutes: List<RowResponse<Double>>,
        val per5Minutes: List<RowResponse<Double>>,
        val per15Minutes: List<RowResponse<Double>>,
        val bytesPerTransfer: List<RowResponse<Long>>,
        val transfersPerSecond: List<RowResponse<Long>>,
        val bytesPerSecond: List<RowResponse<Long>>
    ) {

        constructor(view: FindIOStatsUseCase.Output) : this(
            user = view.user.map { RowResponse(it) },
            system = view.system.map { RowResponse(it) },
            idle = view.idle.map { RowResponse(it) },
            per1Minutes = view.per1Minutes.map { RowResponse(it) },
            per5Minutes = view.per5Minutes.map { RowResponse(it) },
            per15Minutes = view.per15Minutes.map { RowResponse(it) },
            bytesPerTransfer = view.bytesPerTransfer.map { RowResponse(it) },
            transfersPerSecond = view.transfersPerSecond.map { RowResponse(it) },
            bytesPerSecond = view.bytesPerSecond.map { RowResponse(it) },
        )
    }

    class RowResponse<T> private constructor(
        val recordAt: OffsetDateTime,
        val value: T
    ) {
        constructor(result: FindIOStatsUseCase.Row<T>) : this(
            recordAt = result.recordAt,
            value = result.value
        )
    }
}