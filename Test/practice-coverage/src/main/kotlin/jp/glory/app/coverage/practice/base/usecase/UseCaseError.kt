package jp.glory.app.coverage.practice.base.usecase

import jp.glory.app.coverage.practice.base.domain.*

sealed class UseCaseError

class UseCaseUnknownError(
    val cause: Throwable
) : UseCaseError() {
    constructor(error: DomainUnknownError) : this(
        cause = error.cause
    )
}

class UseCaseValidationError(
    val details: List<UseCaseValidationErrorDetail>
) : UseCaseError() {
    constructor(error: SpecError) : this(
        details = error.details.map { toErrorDetail(it) }
    )
}

class UseCaseNotFoundError(
    val resourceName: ResourceName,
    val idValue: String,
) : UseCaseError() {
    enum class ResourceName {
        Product
    }
}

sealed class UseCaseValidationErrorDetail {
    abstract fun toMessage(): String
}

class UseCaseDuplicateKeyErrorDetail(
    val keyName: KeyName,
    val inputValue: String,
) : UseCaseValidationErrorDetail() {
    constructor(error: DuplicateKeyErrorDetail) : this(
        keyName = when (error.keyName) {
            DuplicateKeyErrorDetail.KeyName.ProductCode -> KeyName.ProductCode
        },
        inputValue = error.inputValue
    )

    enum class KeyName {
        ProductCode
    }

    override fun toMessage() = "$keyName is duplicate(input: $inputValue)"
}

fun toUseCaseError(domainError: DomainError): UseCaseError =
    when (domainError) {
        is DomainUnknownError -> UseCaseUnknownError(domainError)
        is SpecError -> UseCaseValidationError(domainError)
    }

private fun toErrorDetail(
    detail: SpecErrorDetail
): UseCaseValidationErrorDetail =
    when (detail) {
        is DuplicateKeyErrorDetail -> UseCaseDuplicateKeyErrorDetail(detail)
    }


