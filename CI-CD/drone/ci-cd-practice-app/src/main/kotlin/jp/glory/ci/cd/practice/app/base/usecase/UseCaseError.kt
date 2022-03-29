package jp.glory.ci.cd.practice.app.base.usecase

import jp.glory.ci.cd.practice.app.base.domain.DomainError
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError


fun toUseCaseError(domainError: DomainError): UseCaseError =
    when (domainError) {
        is DomainUnknownError -> UseCaseUnknownError(domainError)
    }

sealed class UseCaseError

class UseCaseUnknownError(
    val errorMessage: String,
    val cause: Throwable
) : UseCaseError() {
    constructor(error: DomainUnknownError) : this(
        errorMessage = error.message,
        cause = error.cause
    )
}

class UseCaseNotFoundError(
    val resourceName: String,
) : UseCaseError()

class AuthenticationError(
    val type: Type
) : UseCaseError() {
    enum class Type {
        User,
        Token
    }
}
