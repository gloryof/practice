package jp.glory.practice.bench.app.base.api

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.practice.bench.app.base.usecase.UseCaseErrors
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception


@ControllerAdvice
class ExceptionHandler(
    private val mapper: ObjectMapper
) : ResponseEntityExceptionHandler() {
    private val exLogger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(InvalidRequestException::class)
    protected fun invalidRequestException(
        ex: InvalidRequestException
    ): ResponseEntity<ErrorResponse> {
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
        return ResponseEntity.badRequest().body(errorResponse)
    }


    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        exLogger.error("Fatal error", ex)

        return ResponseEntity(body, headers, status)
    }

    data class WarnLogData(
        val summary: String,
        val errors: UseCaseErrors
    )
}