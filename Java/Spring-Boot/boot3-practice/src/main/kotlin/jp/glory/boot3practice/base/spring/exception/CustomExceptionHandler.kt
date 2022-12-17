package jp.glory.boot3practice.base.spring.exception

import jp.glory.boot3practice.base.adaptor.web.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.Exception
import java.net.URI
import javax.naming.AuthenticationException

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @Suppress("UNCHECKED_CAST")
    override fun handleExceptionInternal(
        exception: Exception,
        body: Any?,
        headers: HttpHeaders?,
        status: HttpStatusCode,
        exchange: ServerWebExchange
    ): Mono<ResponseEntity<Any>> =
        createErrorResponse(
            exchange = exchange,
            exception = exception,
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            errorCode = WebErrorCode.ERR500,
            errorDetail = WebUnknownErrorDetail
        )
            .let { toMonoEntity(it) } as Mono<ResponseEntity<Any>>


    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        exception: AuthenticationException,
        exchange: ServerWebExchange
    ): Mono<ResponseEntity<Any>> {
        return handleException(exception, exchange)
    }

    @ExceptionHandler(WebNotFoundException::class)
    fun handleWebNotFoundException(
        exception: WebNotFoundException,
        exchange: ServerWebExchange
    ): Mono<ResponseEntity<ErrorResponse>> =
        createErrorResponse(
            exchange = exchange,
            exception = exception,
            httpStatus = HttpStatus.NOT_FOUND,
            errorCode = exception.errorCode,
            errorDetail = exception.errorDetail
        )
            .let { toMonoEntity(it) }
    private fun createErrorResponse(
        exchange: ServerWebExchange,
        exception: Throwable,
        httpStatus: HttpStatus,
        errorCode: WebErrorCode,
        errorDetail: WebErrorDetail
    ): ErrorResponse {
        val builder = ErrorResponse.builder(
            exception,
            httpStatus,
            errorDetail.getMessage()
        )
            .type(URI.create(exchange.request.path.value()))
            .title(errorCode.message)
            .titleMessageCode(errorCode.name)
            .detailMessageCode(errorDetail.getCode().name)
            .detailMessageArguments(*errorDetail.getMesssageArgument())

        return builder.build()
    }

    private fun toMonoEntity(
        errorResponse: ErrorResponse
    ): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity
            .status(errorResponse.statusCode)
            .body(errorResponse)
            .let { Mono.just(it) }
}