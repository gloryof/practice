package jp.glory.oauth.practice.resource.base.domain

sealed class DomainError

data class DomainUnknownError(
    val message: String,
    val cause: Throwable
): DomainError()

data class UserError(
    val type: Type
): DomainError() {
    enum class Type {
        UserNotFound
    }
}

data class AuthenticateError(
    val type: Type
): DomainError() {
    enum class Type {
        IdentifierNotFound
    }
}