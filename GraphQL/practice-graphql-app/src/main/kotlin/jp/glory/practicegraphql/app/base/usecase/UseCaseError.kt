package jp.glory.practicegraphql.app.base.usecase

import jp.glory.practicegraphql.app.base.domain.DomainError
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.base.domain.SpecError

sealed class UseCaseError(
    open val message: String
)

class UseCaseUnknownError(
    override val message: String,
    val cause: Throwable
) : UseCaseError(message) {
    constructor(error: DomainUnknownError) : this(
        message = error.message,
        cause = error.cause
    )
}

class UseCaseValidationError(
    override val message: String,
    val details: List<UseCaseValidationErrorDetail>
) : UseCaseError(message) {
    constructor(error: SpecError) : this(
        message = error.message,
        details = error.details.map { UseCaseValidationErrorDetail(it.toAttributes()) }
    )
}

class UseCaseNotFoundError(
    override val message: String,
    val resourceName: ResourceName,
    val idValue: String,
) : UseCaseError(message) {
    enum class ResourceName {
        Product
    }
}


data class UseCaseValidationErrorDetail(
    val attributes: Map<String, String>
)

fun toUseCaseError(domainError: DomainError): UseCaseError =
    when (domainError) {
        is DomainUnknownError -> UseCaseUnknownError(domainError)
        is SpecError -> UseCaseValidationError(domainError)
    }
