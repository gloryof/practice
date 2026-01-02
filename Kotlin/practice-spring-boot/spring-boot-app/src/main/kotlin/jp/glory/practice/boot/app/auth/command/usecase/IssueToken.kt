package jp.glory.practice.boot.app.auth.command.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.get
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import jp.glory.practice.boot.app.auth.command.domain.event.AuthEventHandler
import jp.glory.practice.boot.app.auth.command.domain.event.TokenIssued
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.UserCredential
import jp.glory.practice.boot.app.auth.command.domain.repository.UserCredentialRepository
import jp.glory.practice.boot.app.base.Usecase.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.Usecase.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import java.time.Clock

class IssueToken(
    private val repository: UserCredentialRepository,
    private val eventHandler: AuthEventHandler,
    private val clock: Clock
) {

    fun issue(input: Input): Result<String, UsecaseErrors> =
        findByLoginId(input.loginId)
            .flatMap { issueToken(it, input) }
            .map {
                eventHandler.handleTokenIssued(it)
                it.token.value
            }

    private fun findByLoginId(inputLoginId: String): Result<UserCredential, UsecaseErrors> {
        val loginId: LoginId = requireNotNull(LoginId.of(inputLoginId).get())
        return repository.findByLoginId(loginId)
            ?.let { Ok(it) }
            ?: Err(
                UsecaseErrors(
                    specErrors = listOf(UsecaseSpecErrorType.AUTHENTICATED_IS_FAIL)
                )
            )

    }

    private fun issueToken(
        userCredential: UserCredential,
        input: Input
    ): Result<TokenIssued, UsecaseErrors> =
        userCredential.issueToken(
            inputPassword = input.password,
            clock = clock
        )
            .mapError {
                UsecaseErrors.fromDomainError(
                    DomainErrors(
                        specErrors = listOf(it)
                    )
                )
            }

    data class Input(
        val loginId: String,
        val password: String
    )
}
