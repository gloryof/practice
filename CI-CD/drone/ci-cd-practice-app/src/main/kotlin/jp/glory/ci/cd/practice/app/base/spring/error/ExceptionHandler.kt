package jp.glory.ci.cd.practice.app.base.spring.error

import jp.glory.ci.cd.practice.app.base.web.ErrorResponse
import jp.glory.ci.cd.practice.app.base.web.WebAuthenticationException
import jp.glory.ci.cd.practice.app.base.web.WebNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(WebAuthenticationException::class)
    fun handleAuthenticationException(
        ex: WebAuthenticationException
    ): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ErrorResponse(
                    message = "Authentication is fail."
                )
            )

    @ExceptionHandler(WebNotFoundException::class)
    fun handleWebNotFoundException(
        ex: WebNotFoundException
    ): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = ex.message
                )
            )

    // 本当はちゃんとValidationの処理をすべきなんだけど、
    // 今回の勉強用コードでValidationに時間をかけたくなかったのでIllegalArgumentExceptionで対応。
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    message = "Input value is invalid."
                )
            )
}
