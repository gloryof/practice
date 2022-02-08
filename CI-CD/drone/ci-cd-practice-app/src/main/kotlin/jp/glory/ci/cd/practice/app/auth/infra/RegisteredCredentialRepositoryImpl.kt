package jp.glory.ci.cd.practice.app.auth.infra

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.domain.model.*
import jp.glory.ci.cd.practice.app.auth.domain.repository.RegisteredCredentialRepository
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError
import org.springframework.stereotype.Repository

@Repository
class RegisteredCredentialRepositoryImpl : RegisteredCredentialRepository {
    private val credential1 = RegisteredCredential(
        credentialUserId = CredentialUserId("test-user-id"),
        givenName = CredentialGivenName("given-name1"),
        familyName = CredentialFamilyName("family-name1"),
        password = Password("test-password")
    )
    private val credentials = mapOf(
        credential1.credentialUserId.value to credential1
    )
    override fun findById(
        credentialUserId: CredentialUserId
    ): Result<RegisteredCredential?, DomainUnknownError> =
        Ok(credentials[credentialUserId.value])
}