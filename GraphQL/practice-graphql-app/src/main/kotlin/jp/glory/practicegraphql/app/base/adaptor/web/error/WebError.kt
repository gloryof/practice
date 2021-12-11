package jp.glory.practicegraphql.app.base.adaptor.web.error

import jp.glory.practicegraphql.app.base.usecase.UseCaseError
import jp.glory.practicegraphql.app.base.usecase.UseCaseNotFoundError
import jp.glory.practicegraphql.app.base.usecase.UseCaseUnknownError
import jp.glory.practicegraphql.app.base.usecase.UseCaseValidationError

sealed class WebError(
    open val message: String
) {
    fun createException(): WebException = WebException(this)
}

data class WebUnknownError(
    override val message: String,
    val cause: Throwable
) : WebError(message) {
    constructor(error: UseCaseUnknownError) : this(
        message = error.message,
        cause = error.cause
    )
}

class WebValidationError(
    override val message: String,
    val details: List<WebValidationErrorDetail>
) : WebError(message) {
    constructor(error: UseCaseValidationError) : this(
        message = error.message,
        details = error.details.map { WebValidationErrorDetail(it.attributes) }
    )
}

class WebNotFoundError(
    override val message: String,
    val resourceName: String,
    val idValue: String,
) : WebError(message) {
    constructor(error: UseCaseNotFoundError) : this(
        message = error.message,
        resourceName = error.resourceName.name,
        idValue = error.idValue
    )
}

data class WebValidationErrorDetail(
    val attributes: Map<String, String>
)


fun toWebError(useCaseError: UseCaseError): WebError =
    when (useCaseError) {
        is UseCaseUnknownError -> WebUnknownError(useCaseError)
        is UseCaseValidationError -> WebValidationError(useCaseError)
        is UseCaseNotFoundError -> WebNotFoundError(useCaseError)
    }
