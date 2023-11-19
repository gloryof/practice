package jp.glory.practice.measure.architecture.base.adaptor.web

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
class WebExceptionHandler : ResponseEntityExceptionHandler() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(WebException::class)
    fun webException(
        ex: WebException,
    ): ResponseEntity<ProblemDetail> =
        when (ex.error) {
            is WebUnknownError -> handleWebUnknownError(ex.error)
            is WebNotFoundError -> handleNotFoundError(ex.error)
            is WebValidationError -> handleValidationError(ex.error)
        }
            .let {
                ResponseEntity.status(it.status).body(it)
            }

    private fun handleWebUnknownError(
        error: WebUnknownError
    ): ProblemDetail {
        logger.error(error.message, error.cause)

        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .apply {
                detail = "Unknown error is occurred."
            }
    }

    private fun handleNotFoundError(
        error: WebNotFoundError
    ): ProblemDetail =
        ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
            .apply {
                detail = "${error.resourceName} is not found."
                setProperty("id", error.idValue)
                setProperty("resource", error.resourceName)
            }

    private fun handleValidationError(
        error: WebValidationError
    ): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
            .apply {
                detail = "Invalid input."
            }

        error.details.forEachIndexed { index, detail ->
            problemDetail.setProperty("id-${index}", index)
            problemDetail.setProperty("message-${index}", detail.createMessage())
        }

        return problemDetail
    }
}