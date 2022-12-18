package jp.glory.boot3practice.base.spring.exception

import jp.glory.boot3practice.base.adaptor.web.*
import org.springframework.http.HttpHeaders
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
        WebGenericErrorDetail.createGenericErrorDetail(
            httpStatusCode = status
        )
            .let {
                createErrorResponse(
                    exchange = exchange,
                    exception = exception,
                    errorDetail = it
                )
            }
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
            errorDetail = exception.errorDetail
        )
            .let { toMonoEntity(it) }
    private fun createErrorResponse(
        exchange: ServerWebExchange,
        exception: Throwable,
        errorDetail: WebErrorDetail
    ): ErrorResponse {
        val builder = ErrorResponse.builder(
            exception,
            errorDetail.getHttpStatus(),
            errorDetail.getErrorDetailMessage()
        )
            .type(URI.create(exchange.request.path.value()))
            .title(errorDetail.getErrorMessage())
            .titleMessageCode(errorDetail.getErrorCodeValue())
            .detailMessageCode(errorDetail.getErrorDetailCodeValue())
            .detailMessageArguments(*errorDetail.getErrorDetailMessageArgument())

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