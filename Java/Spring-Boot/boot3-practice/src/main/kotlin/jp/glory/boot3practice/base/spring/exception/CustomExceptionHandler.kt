package jp.glory.boot3practice.base.spring.exception

import jp.glory.boot3practice.base.adaptor.web.WebNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import javax.naming.AuthenticationException

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

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
        ErrorResponse.builder(
            exception,
            HttpStatus.NOT_FOUND,
            exception.message
        )
            .build()
            .let {
                ResponseEntity
                    .status(it.statusCode)
                    .body(it)
            }
            .let { Mono.just(it) }
}