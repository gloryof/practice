package jp.glory.practice.boot.app.auth.command.domain.model

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.auth.command.domain.event.TokenIssued
import jp.glory.practice.boot.app.base.domain.exception.DomainSpecErrorType
import jp.glory.practice.boot.app.user.command.domain.model.UserId
import java.time.Clock

class UserCredential(
    val loginId: LoginId,
    val userId: UserId,
    private val password: Password
) {
    fun issueToken(
        inputPassword: String,
        clock: Clock
    ): Result<TokenIssued, DomainSpecErrorType> {
        if (password.value == inputPassword) {
            return Ok(createIssueTokenEvent(clock))
        }

        return Err(DomainSpecErrorType.AUTHENTICATED_IS_FAIL)
    }

    private fun createIssueTokenEvent(clock: Clock): TokenIssued =
        TokenIssued(
            userId = userId,
            token = AuthToken.issue(clock),
        )
}
