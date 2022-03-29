package jp.glory.ci.cd.practice.app.auth.infra

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.domain.model.*
import jp.glory.ci.cd.practice.app.auth.domain.repository.RegisteredCredentialRepository
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError
import org.springframework.stereotype.Repository

@Repository
class RegisteredCredentialRepositoryImpl(
    private val dao: RegisterCredentialDao
) : RegisteredCredentialRepository {

    override fun findById(
        credentialUserId: CredentialUserId
    ): Result<RegisteredCredential?, DomainUnknownError> =
        dao.findById(credentialUserId.value)
            ?.let {
                RegisteredCredential(
                    credentialUserId = CredentialUserId(it.credentialUserId),
                    givenName = CredentialGivenName(it.givenName),
                    familyName = CredentialFamilyName(it.familyName),
                    password = Password(it.password)
                )
            }
            .let { Ok(it) }
}