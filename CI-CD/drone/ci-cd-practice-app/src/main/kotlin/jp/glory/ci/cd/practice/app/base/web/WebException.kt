package jp.glory.ci.cd.practice.app.base.web

import jp.glory.ci.cd.practice.app.base.usecase.AuthenticationError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseNotFoundError
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseUnknownError

open class WebException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

class WebAuthenticationException : WebException(
    message = "Authentication is fail"
)

class WebNotFoundException(
    resourceName: String
) : WebException(
    message = "$resourceName is not found"
)

class InternalServerErrorException(
    val errorMessage: String,
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
            is AuthenticationError -> WebAuthenticationException()
            is UseCaseNotFoundError -> WebNotFoundException(useCaseError.resourceName)
            is UseCaseUnknownError -> InternalServerErrorException(
                errorMessage = useCaseError.errorMessage,
                cause = useCaseError.cause
            )
        }
}
