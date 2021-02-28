package jp.glory.oauth.practice.resource.context.auth.domain.repository

import jp.glory.oauth.practice.resource.context.auth.domain.model.IdentifierCredential
import jp.glory.oauth.practice.resource.base.Either
import jp.glory.oauth.practice.resource.base.domain.DomainUnknownError

interface IdentifierCredentialRepository {
    fun findByLoginId(loginId: String): Either<DomainUnknownError, IdentifierCredential?>
}