package jp.glory.ci.cd.practice.app.auth.usecase

import com.github.michaelbull.result.*
import jp.glory.ci.cd.practice.app.auth.domain.model.*
import jp.glory.ci.cd.practice.app.auth.domain.repository.RegisteredCredentialRepository
import jp.glory.ci.cd.practice.app.auth.domain.repository.TokenRepository
import jp.glory.ci.cd.practice.app.base.usecase.AuthenticationError
import jp.glory.ci.cd.practice.app.base.usecase.UseCase
import jp.glory.ci.cd.practice.app.base.usecase.UseCaseError
import jp.glory.ci.cd.practice.app.base.usecase.toUseCaseError

@UseCase
class AuthenticateToken(
    private val tokenRepository: TokenRepository,
    private val credentialRepository: RegisteredCredentialRepository,
) {
    class Input(
        val tokenValue: String
    )

    class Output(
        val credentialUserId: String,
        val givenName: String,
        val familyName: String,
    )

    fun authenticate(input: Input): Result<Output, UseCaseError> =
        findToken(input.tokenValue)
            .flatMap { findCredential(it.credentialUserId) }
            .map {toOutput(it) }

    private fun findToken(
        tokenValue: String
    ): Result<RegisteredToken, UseCaseError> =
        tokenRepository.findToken(TokenValue(tokenValue))
            .mapError { toUseCaseError(it) }
            .flatMap {
                if (it != null) { Ok(it) } else { createAuthenticationError() }
            }

    private fun findCredential(
        credentialUserId: CredentialUserId
    ): Result<RegisteredCredential, UseCaseError> =
        credentialRepository.findById(credentialUserId)
            .mapError { toUseCaseError(it) }
            .flatMap {
                if (it != null) { Ok(it) } else { createAuthenticationError() }
            }

    private fun toOutput(registeredCredential: RegisteredCredential): Output =
        Output(
            credentialUserId = registeredCredential.credentialUserId.value,
            givenName = registeredCredential.givenName.value,
            familyName = registeredCredential.familyName.value,
        )

    private fun createAuthenticationError(): Err<AuthenticationError> =
        Err(
            AuthenticationError(
                type = AuthenticationError.Type.Token
            )
        )
}