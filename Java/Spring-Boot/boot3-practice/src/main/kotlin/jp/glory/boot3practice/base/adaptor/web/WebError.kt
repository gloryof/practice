package jp.glory.boot3practice.base.adaptor.web

import jp.glory.boot3practice.base.use_case.*
import org.springframework.security.core.userdetails.UsernameNotFoundException

open class WebException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

class WebValidationException(
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

class InternalServerError(
    cause: Throwable
) : WebException(
    message = "Unknown error is occurred",
    cause = cause
)

object WebExceptionHelper {
    fun create(
        useCaseError: UseCaseError
    ): Throwable =
        when (useCaseError) {
            is UseCaseValidationError -> WebValidationException(
                useCaseError.details.map { it.toMessage() }
            )
            is UseCaseNotFoundError -> WebNotFoundException(
                resourceName = useCaseError.resourceName.name,
                idValue = useCaseError.idValue
            )
            is UseCaseUnknownError -> InternalServerError(
                cause = useCaseError.cause
            )
            is UseCaseAuthenticationError -> UsernameNotFoundException(
                "Authentication is failed."
            )
        }
}