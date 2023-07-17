package jp.glory.ktor.practice.auth.use_case

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ktor.practice.auth.domain.event.*
import jp.glory.ktor.practice.auth.domain.model.Credential
import jp.glory.ktor.practice.auth.domain.model.Token
import jp.glory.ktor.practice.auth.domain.repository.CredentialRepository
import jp.glory.ktor.practice.auth.domain.model.UserId
import jp.glory.ktor.practice.auth.domain.repository.TokenRepository
import jp.glory.ktor.practice.base.use_case.UseCaseAuthenticationError
import jp.glory.ktor.practice.base.use_case.UseCaseError

class AuthenticateUseCase(
    private val credentialRepository: CredentialRepository,
    private val tokenRepository: TokenRepository
) {
    class UserAuthInput(
        val id: String,
        val password: String
    )

    class UserAuthOutput(
        val tokenValue: String
    )

    class TokenAuthInput(
        val tokenValue: String
    )

    class TokenAuthOutput(
        val id: String
    )

    fun authenticateUser(input: UserAuthInput): Result<UserAuthOutput, UseCaseError> {
        val event = UserAuthenticateEvent(
            id = InputUserId(input.id),
            password = InputPassword(input.password)
        )

        return findCredential(event.id)
            ?.authenticate(event)
            ?.let { Token.generate(it) }
            ?.also { tokenRepository.save(it) }
            ?.let { Ok(UserAuthOutput(it.tokenValue.value)) }
            ?: Err(UseCaseAuthenticationError(UseCaseAuthenticationError.Type.User))
    }

    fun authenticateToken(input: TokenAuthInput): Result<TokenAuthOutput, UseCaseError> {
        val event = TokenAuthenticateEvent(InputToken(input.tokenValue))

        return tokenRepository.findToken(event)
            ?.let { Ok(TokenAuthOutput(it.userId.getValue())) }
            ?: Err(UseCaseAuthenticationError(UseCaseAuthenticationError.Type.Token))
    }

    private fun findCredential(userId: InputUserId): Credential? =
        credentialRepository.findById(UserId(userId.value))

}
