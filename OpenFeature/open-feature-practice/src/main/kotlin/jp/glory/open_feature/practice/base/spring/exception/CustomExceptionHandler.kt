package jp.glory.open_feature.practice.base.spring.exception

import jp.glory.open_feature.practice.base.adaptor.web.WebNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(WebNotFoundException::class)
    fun webNotFoundException(ex: WebNotFoundException): ResponseEntity<ErrorResponse> =
        ErrorResponse.create(
            ex,
            HttpStatus.NOT_FOUND,
            ex.message
        )
            .let {
                ResponseEntity.status(it.statusCode).body(it)
            }
}