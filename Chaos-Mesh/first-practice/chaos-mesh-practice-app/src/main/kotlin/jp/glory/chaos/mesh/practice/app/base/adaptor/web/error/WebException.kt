package jp.glory.chaos.mesh.practice.app.base.adaptor.web.error

import jp.glory.chaos.mesh.practice.app.base.usecase.UseCaseError
import jp.glory.chaos.mesh.practice.app.base.usecase.UseCaseNotFoundError
import jp.glory.chaos.mesh.practice.app.base.usecase.UseCaseUnknownError
import jp.glory.chaos.mesh.practice.app.base.usecase.UseCaseValidationError

open class WebException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

class ValidationErrorException(
    val details: List<String>
) : WebException(
    message = "Input value is invalid."
)


class WebNotFoundException(
    resourceName: String,
    idValue: String
) : WebException(
    message = "$resourceName is not found(id: $idValue)"
)

class InternalServerErrorException(
    cause: Throwable
) : WebException(
    message = "Unknown error is occurred",
    cause = cause
)

object WebExceptionHelper {
    fun create(
        useCaseError: UseCaseError
    ): WebException =
        when (useCaseError) {
            is UseCaseValidationError -> ValidationErrorException(
                useCaseError.details.map { it.toMessage() }
            )
            is UseCaseNotFoundError -> WebNotFoundException(
                resourceName = useCaseError.resourceName.name,
                idValue = useCaseError.idValue
            )
            is UseCaseUnknownError -> InternalServerErrorException(
                cause = useCaseError.cause
            )
        }
}
