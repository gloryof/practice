package jp.glory.oauth.practice.authorization.base

sealed class ApplicationError

data class ServerError(
    val message: String,
    val cause: Throwable
) : ApplicationError()

data class AuthorizationError(
    val message: String,
) : ApplicationError()