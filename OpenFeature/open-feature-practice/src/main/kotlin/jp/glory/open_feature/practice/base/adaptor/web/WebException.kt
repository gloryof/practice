package jp.glory.open_feature.practice.base.adaptor.web

import jp.glory.open_feature.practice.base.use_case.UseCaseError
import jp.glory.open_feature.practice.base.use_case.UseCaseNotFoundError
import jp.glory.open_feature.practice.base.use_case.UseCaseUnknownError

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
    val resourceName: String,
    val idValue: String,
    message: String
) : WebException(message) {
    companion object {
        fun create(
            resourceName: String,
            idValue: String
        ): WebNotFoundException =
            WebNotFoundException(
                resourceName = resourceName,
                idValue = idValue,
                message = "Not found"
            )

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
        }
}