package jp.glory.practicegraphql.app.base.usecase

import jp.glory.practicegraphql.app.base.domain.DomainError
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError

sealed class UseCaseError

data class UseCaseUnknownError(
    val message: String,
    val cause: Throwable
) : UseCaseError() {
    constructor(error: DomainUnknownError): this(
        message = error.message,
        cause = error.cause
    )
}

fun toUseCaseError(domainError: DomainError): UseCaseError =
    when(domainError) {
        is DomainUnknownError -> UseCaseUnknownError(domainError)
    }