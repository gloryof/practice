package jp.glory.practice.measure.architecture.base.usecase

import jp.glory.practice.measure.architecture.base.domain.DomainError
import jp.glory.practice.measure.architecture.base.domain.DomainUnknownError
import jp.glory.practice.measure.architecture.base.domain.DuplicateKeyErrorDetail
import jp.glory.practice.measure.architecture.base.domain.SpecError
import jp.glory.practice.measure.architecture.base.domain.SpecErrorDetail

sealed class UseCaseError

class UseCaseUnknownError(
    val message: String,
    val cause: Throwable
) : UseCaseError() {
    constructor(error: DomainUnknownError) : this(
        message = error.message,
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

sealed class UseCaseValidationErrorDetail

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