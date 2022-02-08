package jp.glory.ci.cd.practice.app.base.domain

sealed class DomainError

class DomainUnknownError(
    val message: String,
    val cause: Throwable
) : DomainError()
