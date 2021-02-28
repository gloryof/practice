package jp.glory.oauth.practice.resource.base.usecase

import jp.glory.oauth.practice.resource.base.domain.AuthenticateError
import jp.glory.oauth.practice.resource.base.domain.DomainError
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError
import jp.glory.oauth.practice.resource.base.domain.UserError

sealed class UseCaseError

data class UseCaseUnknownError(
    val message: String,
    val cause: Throwable
): UseCaseError() {
    constructor(error: DomainUnknownError) : this(
        message = error.message,
        cause = error.cause
    )
}

data class UseCaseUserError(
    val type: Type,
): UseCaseError() {
    companion object {
        private fun mapToUseCaseType(type: UserError.Type) =
            when(type) {
                UserError.Type.UserNotFound -> Type.UserNotFound
            }
    }
    constructor(error: UserError): this(
        mapToUseCaseType(error.type)
    )

    enum class Type {
        UserNotFound
    }
}

data class UseCaseAuthenticateError(
    val type: Type,
): UseCaseError() {
    companion object {
        private fun mapToUseCaseType(type: AuthenticateError.Type) =
            when(type) {
                AuthenticateError.Type.IdentifierNotFound -> Type.IdentifierNotFound
            }
    }
    constructor(error: AuthenticateError): this(
        mapToUseCaseType(error.type)
    )

    enum class Type {
        IdentifierNotFound,
        AuthenticationFailed
    }
}

fun mapToUseCaseError(error: DomainError): UseCaseError =
    when(error) {
        is AuthenticateError -> UseCaseAuthenticateError(error)
        is UserError -> UseCaseUserError(error)
        is DomainUnknownError -> UseCaseUnknownError(error)

    }