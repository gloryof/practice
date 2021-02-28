package jp.glory.oauth.practice.resource.context.auth.usecase

import jp.glory.oauth.practice.resource.base.*
import jp.glory.oauth.practice.resource.context.auth.domain.model.EncryptedPassword
import jp.glory.oauth.practice.resource.context.auth.domain.model.IdentifierCredential
import jp.glory.oauth.practice.resource.context.auth.domain.repository.IdentifierCredentialRepository
import jp.glory.oauth.practice.resource.base.domain.AuthenticateError
import jp.glory.oauth.practice.resource.base.usecase.UseCase
import jp.glory.oauth.practice.resource.base.usecase.UseCaseError
import jp.glory.oauth.practice.resource.base.usecase.mapToUseCaseError

@UseCase
class FindIdentifierCredential(
    private val repository: IdentifierCredentialRepository,
) {
    fun findByLoginId(loginId: String): Either<UseCaseError, CredentialResult> =
        repository.findByLoginId(loginId)
            .flatMap{ validateIdentifierCredential(it) }
            .map {
                CredentialResult(
                    loginId = it.loginId,
                    encryptedPassword = it.password.value.value
                )
            }
            .mapBoth(
                right = { Right(it) },
                left = { Left(mapToUseCaseError(it)) }
            )

    data class CredentialResult(
        val loginId: String,
        val encryptedPassword: String,
    )

    private fun validateIdentifierCredential(
        value: IdentifierCredential?
    ): Either<AuthenticateError, IdentifierCredential> {
        if(value == null) {
            return Left(AuthenticateError(AuthenticateError.Type.IdentifierNotFound))
        }

        return Right(value)
    }

}