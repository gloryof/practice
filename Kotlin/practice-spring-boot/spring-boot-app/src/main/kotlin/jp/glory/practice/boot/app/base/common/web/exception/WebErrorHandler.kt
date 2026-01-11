package jp.glory.practice.boot.app.base.common.web.exception

import org.springframework.http.HttpStatus
import org.springframework.web.servlet.function.ServerResponse

object WebErrorHandler {
    fun createErrorResponse(errors: WebErrors): ServerResponse {
        val detail = createBody(errors)

        return ServerResponse.status(detail.status)
            .body(detail)
    }

    private fun createBody(errors: WebErrors): ErrorResponse =
        ErrorResponse(
            status = getStatus(errors).value(),
            title = "Your request is invalid",
            errors = toErrorDetails(errors)
        )

    private fun getStatus(errors: WebErrors): HttpStatus {
        if (errors.specErrors.contains(WebSpecErrorType.UNAUTHORIZED)) {
            return HttpStatus.UNAUTHORIZED
        }
        if (errors.specErrors.contains(WebSpecErrorType.DATA_IS_NOT_FOUND)) {
            return HttpStatus.NOT_FOUND
        }
        return HttpStatus.BAD_REQUEST
    }

    private fun toErrorDetails(error: WebErrors): List<ErrorDetail> {
        val results = mutableListOf<ErrorDetail>()

        error.itemErrors
            .forEach {
                results.add(
                    ErrorDetail(
                        name = it.name,
                        errorTypes = it.errors.map { type -> type.name }
                    )
                )
            }

        error.specErrors
            .forEach {
                results.add(
                    ErrorDetail(
                        name = "",
                        errorTypes = listOf(it.name)
                    )
                )
            }

        return results
    }
}
