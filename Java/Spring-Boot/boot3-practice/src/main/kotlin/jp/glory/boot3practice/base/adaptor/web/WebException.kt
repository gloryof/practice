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

class WebNotFoundException private constructor(
    val errorCode: WebErrorCode,
    val errorDetail: WebNotFoundErrorDetail
) : WebException(errorDetail.getMessage()) {
    companion object {
        fun create(
            resourceName: String,
            idValue: String
        ): WebNotFoundException =
            WebNotFoundErrorDetail(
                resourceName = resourceName,
                idValue = idValue
            )
                .let {
                    WebNotFoundException(
                        errorCode = WebErrorCode.ERR404,
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
            is UseCaseAuthenticationError -> UsernameNotFoundException(
                "Authentication is failed."
            )
        }
}