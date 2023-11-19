package jp.glory.practice.measure.architecture.base.adaptor.web

import jp.glory.practice.measure.architecture.base.usecase.UseCaseDuplicateKeyErrorDetail
import jp.glory.practice.measure.architecture.base.usecase.UseCaseError
import jp.glory.practice.measure.architecture.base.usecase.UseCaseNotFoundError
import jp.glory.practice.measure.architecture.base.usecase.UseCaseUnknownError
import jp.glory.practice.measure.architecture.base.usecase.UseCaseValidationError
import jp.glory.practice.measure.architecture.base.usecase.UseCaseValidationErrorDetail

sealed class WebError {
    fun createException(): WebException = WebException(this)
}

data class WebUnknownError(
    val message: String,
    val cause: Throwable
) : WebError() {
    constructor(error: UseCaseUnknownError) : this(
        message = error.message,
        cause = error.cause
    )
}

class WebValidationError(
    val details: List<WebValidationErrorDetail>
) : WebError() {
    constructor(error: UseCaseValidationError) : this(
        details = error.details.map { toWebValidationErrorDetail(it) }
    )
}

class WebNotFoundError(
    val resourceName: String,
    val idValue: String,
) : WebError() {
    constructor(error: UseCaseNotFoundError) : this(
        resourceName = error.resourceName.name,
        idValue = error.idValue
    )
}

interface WebValidationErrorDetail {
    fun createMessage(): String
    fun toAttribute(): Map<String, String>
}

class WebDuplicateKeyErrorDetail(
    private val keyName: KeyName,
    private val inputValue: String,
) : WebValidationErrorDetail {
    constructor(error: UseCaseDuplicateKeyErrorDetail) : this(
        keyName = when (error.keyName) {
            UseCaseDuplicateKeyErrorDetail.KeyName.ProductCode -> KeyName.ProductCode
        },
        inputValue = error.inputValue
    )

    override fun createMessage(): String =
        "${keyName.name} is duplicate"

    override fun toAttribute(): Map<String, String> =
        mapOf(
            "keyName" to keyName.toString(),
            "inputValue" to inputValue
        )

    enum class KeyName {
        ProductCode
    }
}

fun toWebError(useCaseError: UseCaseError): WebError =
    when (useCaseError) {
        is UseCaseUnknownError -> WebUnknownError(useCaseError)
        is UseCaseValidationError -> WebValidationError(useCaseError)
        is UseCaseNotFoundError -> WebNotFoundError(useCaseError)
    }

private fun toWebValidationErrorDetail(
    error: UseCaseValidationErrorDetail
): WebValidationErrorDetail =
    when (error) {
        is UseCaseDuplicateKeyErrorDetail -> WebDuplicateKeyErrorDetail(error)
    }