package jp.glory.app.coverage.arch_unit.base.adaptor.web.error

import jp.glory.app.coverage.arch_unit.base.usecase.UseCaseError
import jp.glory.app.coverage.arch_unit.base.usecase.UseCaseNotFoundError
import jp.glory.app.coverage.arch_unit.base.usecase.UseCaseUnknownError
import jp.glory.app.coverage.arch_unit.base.usecase.UseCaseValidationError

sealed class WebError(
    val message: String,
    val cause: Throwable? = null
) {
    open fun toErrorResponse(): ErrorResponse =
        ErrorResponse(
            message = message,
            details = emptyList()
        )
}

class WebValidationError(
    private val details: List<String>
) : WebError(
    message = "Input value is invalid."
) {
    override fun toErrorResponse(): ErrorResponse =
        ErrorResponse(
            message = message,
            details = details
        )
}


class WebNotFound(
    resourceName: String,
    idValue: String
) : WebError(
    message = "$resourceName is not found(id: $idValue)"
)

class InternalServerError(
    cause: Throwable
) : WebError(
    message = "Unknown error is occurred",
    cause = cause
)

object WebErrorHelper {
    fun create(
        useCaseError: UseCaseError
    ): WebError =
        when (useCaseError) {
            is UseCaseValidationError -> WebValidationError(
                useCaseError.details.map { it.toMessage() }
            )
            is UseCaseNotFoundError -> WebNotFound(
                resourceName = useCaseError.resourceName.name,
                idValue = useCaseError.idValue
            )
            is UseCaseUnknownError -> InternalServerError(
                cause = useCaseError.cause
            )
        }
}
