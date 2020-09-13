package jp.glory.kofu.app.base.api

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.kofu.app.base.usecase.UseCaseErrors
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.lang.Exception


class ExceptionHandler(
    private val mapper: ObjectMapper
) {
    private val exLogger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    fun handle(throwable: Throwable, request: ServerRequest): Mono<ServerResponse> =
        when(throwable) {
            is InvalidRequestException -> handleInvalidRequestException(throwable)
            else -> handleOther(throwable)
        }

    private fun handleInvalidRequestException(
        ex: InvalidRequestException
    ): Mono<ServerResponse> {
        val data =
            WarnLogData(
                summary = ex.summary,
                errors = ex.errors
            )

        exLogger.warn(
            mapper.writeValueAsString(data)
        )

        val errorResponse = ErrorResponse(
            summary = ex.summary,
            errors = ex.errors
        )
        return ServerResponse.badRequest().bodyValue(errorResponse)
    }


    private fun handleOther(
        ex: Throwable
    ): Mono<ServerResponse> {
        exLogger.error("Fatal error", ex)

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue("error")
    }

    data class WarnLogData(
        val summary: String,
        val errors: UseCaseErrors
    )
}