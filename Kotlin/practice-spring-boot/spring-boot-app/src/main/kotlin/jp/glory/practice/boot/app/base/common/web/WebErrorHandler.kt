package jp.glory.practice.boot.app.base.common.web

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.servlet.function.ServerResponse

object WebErrorHandler {
    fun createErrorResponse(errors: WebErrors): ServerResponse {
        val detail = createProblemDetail(errors)

        return ServerResponse.status(detail.status)
            .body(detail)
    }

    private fun createProblemDetail(errors: WebErrors): ProblemDetail =
        ProblemDetail.forStatusAndDetail(
            getStatus(errors),
            "Your request is invalid"
        )
            .apply {
                title = "Invalid request"

                val details = toErrorDetails(errors)
                if (details.isNotEmpty()) {
                    setProperty("errors", details)
                }
            }

    private fun getStatus(errors: WebErrors): HttpStatus {
        if (errors.specErrors.contains(WebSpecErrorType.NOT_AUTHORIZED)) {
            return HttpStatus.UNAUTHORIZED
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
