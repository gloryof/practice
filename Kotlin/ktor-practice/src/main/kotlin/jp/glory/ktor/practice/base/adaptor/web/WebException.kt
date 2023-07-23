package jp.glory.ktor.practice.base.adaptor.web

import jp.glory.ktor.practice.base.use_case.UseCaseAuthenticationError
import jp.glory.ktor.practice.base.use_case.UseCaseError
import jp.glory.ktor.practice.base.use_case.UseCaseNotFoundError
import jp.glory.ktor.practice.base.use_case.UseCaseUnknownError

open class WebException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

class WebValidationException(
    val details: List<String>
) : WebException(
    message = "Input value is invalid."
)

object WebAuthenticateFailedException : WebException(
    message = "Authentication is failed"
) {
    val errorDetail: WebAuthenticationFailedError = WebAuthenticationFailedError
}

object WebNotAuthorizedException : WebException(
    message = "Not authorized"
) {
    val errorDetail: WebNotAuthorizedError = WebNotAuthorizedError
}

class WebNotFoundException private constructor(
    val errorDetail: WebTargetNotFoundErrorDetail
) : WebException(errorDetail.getErrorDetailMessage()) {
    companion object {
        fun create(
            resourceName: String,
            idValue: String
        ): WebNotFoundException =
            WebTargetNotFoundErrorDetail(
                resourceName = resourceName,
                idValue = idValue
            )
                .let {
                    WebNotFoundException(
                        errorDetail = it
                    )
                }
    }
}

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
            is UseCaseNotFoundError -> WebNotFoundException.create(
                resourceName = useCaseError.resourceName.name,
                idValue = useCaseError.idValue
            )
            is UseCaseUnknownError -> InternalServerError(
                cause = useCaseError.cause
            )
            is UseCaseAuthenticationError -> WebAuthenticateFailedException
        }
}
