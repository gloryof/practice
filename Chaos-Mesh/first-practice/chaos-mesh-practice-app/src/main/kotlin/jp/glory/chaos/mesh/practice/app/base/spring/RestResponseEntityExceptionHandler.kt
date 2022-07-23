package jp.glory.chaos.mesh.practice.app.base.spring

import jp.glory.chaos.mesh.practice.app.base.adaptor.web.error.ErrorResponse
import jp.glory.chaos.mesh.practice.app.base.adaptor.web.error.ValidationErrorException
import jp.glory.chaos.mesh.practice.app.base.adaptor.web.error.ValidationErrorResponse
import jp.glory.chaos.mesh.practice.app.base.adaptor.web.error.WebNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ValidationErrorException::class)
    fun handleValidationErrorException(
        ex: ValidationErrorException
    ): ResponseEntity<ValidationErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ValidationErrorResponse(
                    message = ex.message,
                    details = ex.details
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
}
