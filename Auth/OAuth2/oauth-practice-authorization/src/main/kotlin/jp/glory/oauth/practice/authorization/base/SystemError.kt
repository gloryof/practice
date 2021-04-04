package jp.glory.oauth.practice.authorization.base

sealed class SystemError

data class FatalError(
    val message: String,
    val cause: Throwable
) : SystemError()

data class AuthorizationError(
    val message: String,
) : SystemError()