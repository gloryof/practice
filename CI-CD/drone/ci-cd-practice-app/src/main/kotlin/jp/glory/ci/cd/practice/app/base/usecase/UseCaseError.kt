package jp.glory.ci.cd.practice.app.base.usecase

import jp.glory.ci.cd.practice.app.base.domain.DomainError
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError


fun toUseCaseError(domainError: DomainError): UseCaseError =
    when (domainError) {
        is DomainUnknownError -> UseCaseUnknownError(domainError)
    }

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

class AuthenticationError(
    val type: Type
) : UseCaseError() {
    enum class Type {
        User,
        Token
    }
}
