package jp.glory.practicegraphql.app.base.domain

sealed class DomainError

data class DomainUnknownError(
    val message: String,
    val cause: Throwable
) : DomainError()