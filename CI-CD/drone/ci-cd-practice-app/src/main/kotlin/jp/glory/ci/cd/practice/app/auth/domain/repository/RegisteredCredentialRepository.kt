package jp.glory.ci.cd.practice.app.auth.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.domain.model.CredentialUserId
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredCredential
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError

interface RegisteredCredentialRepository {
    fun findById(credentialUserId: CredentialUserId): Result<RegisteredCredential?, DomainUnknownError>
}