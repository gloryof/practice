package jp.glory.practicegraphql.app.base.usecase

import jp.glory.practicegraphql.app.base.domain.DomainError

sealed class UseCaseError

fun toUseCaseError(domainError: DomainError): UseCaseError =
    TODO("Not yet implement")