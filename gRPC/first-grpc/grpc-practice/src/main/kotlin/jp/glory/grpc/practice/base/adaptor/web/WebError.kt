package jp.glory.grpc.practice.base.adaptor.web

import com.google.rpc.Code
import com.google.rpc.ErrorInfo
import com.google.rpc.Status
import jp.glory.grpc.practice.base.usecase.*


sealed class WebError {
    fun getStatus(): Status = Status.newBuilder()
        .setCode(getCode().number)
        .setMessage(getDescription())
        .addDetails(com.google.protobuf.Any.pack(getErrorInfo()))
        .build()

    open fun getCode(): Code = Code.UNKNOWN
    open fun getDescription(): String = "Unknown error is occurred."
    private fun getErrorInfo(): ErrorInfo =
        ErrorInfo.newBuilder()
            .apply {
                reason = getDescription()
                putAllMetadata(getErrorAttributes())
            }
            .build()
    protected open fun getErrorAttributes(): Map<String, String> = emptyMap()
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
    private val resourceName: String,
    private val idValue: String,
) : WebError() {
    constructor(error: UseCaseNotFoundError) : this(
        resourceName = error.resourceName.name,
        idValue = error.idValue
    )

    override fun getCode(): Code = Code.NOT_FOUND
    override fun getDescription(): String = "Resource is not found."
    override fun getErrorAttributes(): Map<String, String> =
        mapOf(
            "resourceName" to resourceName,
            "idValue" to idValue,
            "test" to "ほげ"
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
