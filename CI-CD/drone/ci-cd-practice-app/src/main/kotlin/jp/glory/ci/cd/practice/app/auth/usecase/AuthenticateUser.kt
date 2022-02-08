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
class AuthenticateUser(
    private val credentialRepository: RegisteredCredentialRepository,
    private val tokenRepository: TokenRepository,
    private val generator: TokenGenerator
) {
    class Input(
        val userId: String,
        val password: String
    )

    class Output(
        val tokenValue: String
    )

    fun authenticate(input: Input): Result<Output, UseCaseError> =
        findCredential(input.userId)
            .flatMap {
                authenticateUser(
                    credential = it,
                    input = input
                )
            }
            .flatMap { generateToken(it) }
            .map { Output(it.value) }

    private fun findCredential(
        userId: String
    ): Result<RegisteredCredential, UseCaseError> =
        credentialRepository.findById(CredentialUserId(userId))
            .mapError { toUseCaseError(it) }
            .flatMap {
                if (it != null) { Ok(it) } else { createAuthenticationError() }
            }

    private fun authenticateUser(
        credential: RegisteredCredential,
        input: Input
    ): Result<RegisteredCredential, AuthenticationError> {
        val isAuthenticated = credential.authenticate(
            credentialUserId = CredentialUserId(input.userId),
            password = Password(input.password)
        )
        if (!isAuthenticated) {
            return createAuthenticationError()
        }

        return Ok(credential)
    }

    private fun generateToken(
        credential: RegisteredCredential,
    ): Result<TokenValue, UseCaseError> {
        val token = generator.generate()

        return tokenRepository.save(
            tokenValue = token,
            registeredCredential = credential
        )
            .mapBoth(
                success = { Ok(it.tokenValue) },
                failure = { Err(toUseCaseError(it)) }
            )
    }

    private fun createAuthenticationError(): Err<AuthenticationError> =
        Err(
            AuthenticationError(
                type = AuthenticationError.Type.User
            )
        )
}