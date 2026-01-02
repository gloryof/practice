package jp.glory.practice.boot.app.auth.command.usecase

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import jp.glory.practice.boot.app.auth.command.domain.event.AuthEventHandler
import jp.glory.practice.boot.app.auth.command.domain.event.TokenIssued
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.Password
import jp.glory.practice.boot.app.auth.command.domain.model.UserCredential
import jp.glory.practice.boot.app.auth.command.domain.repository.UserCredentialRepository
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.test.tool.UsecaseErrorsAssertion
import jp.glory.practice.boot.app.user.command.domain.model.UserId
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.fail
import java.time.Clock
import kotlin.test.assertEquals

class IssueTokenTest {

    @Nested
    inner class TestIssue {
        @Test
        fun success() {
            val input = IssueToken.Input(
                loginId = "test-login-id",
                password = "test-password-123456"
            )
            val userId: UserId = UserIdGenerator().issueNewId()
            val loginId: LoginId = requireNotNull(LoginId.of(input.loginId).get())

            val repository: UserCredentialRepository = mockk()
            val userCredential = UserCredential(
                loginId = loginId,
                userId = userId,
                password = requireNotNull(Password.of(input.password).get())
            )
            every { repository.findByLoginId(loginId) } returns userCredential

            val eventHandler: AuthEventHandler = mockk()
            val slot: CapturingSlot<TokenIssued> = slot()
            every { eventHandler.handleTokenIssued(capture(slot)) } just runs

            val sut = createSut(
                repository = repository,
                eventHandler = eventHandler
            )

            assertNotNull(sut.issue(input).get())

            val actualEvent: TokenIssued = slot.captured
            assertEquals(userId.value, actualEvent.userId.value)
        }

        @Nested
        inner class Failed {
            @Test
            fun whenLoginUserIsNotFound() {
                val input = IssueToken.Input(
                    loginId = "test-login-id",
                    password = "test-password-123456"
                )
                val userId: UserId = UserIdGenerator().issueNewId()
                val loginId: LoginId = requireNotNull(LoginId.of(input.loginId).get())

                val repository: UserCredentialRepository = mockk()
                every { repository.findByLoginId(loginId) } returns null

                val sut = createSut(
                    repository = repository,
                )

                val actual = sut.issue(input).getError() ?: fail("Unexpected success")
                UsecaseErrorsAssertion(
                    specErrors = setOf(UsecaseSpecErrorType.LOGIN_IS_FAIL),
                    usecaseErrors = actual
                )
                    .assertAll()
            }

            @Test
            fun whenInvalidPassword() {
                val input = IssueToken.Input(
                    loginId = "test-login-id",
                    password = "test-password-123456"
                )
                val userId: UserId = UserIdGenerator().issueNewId()
                val loginId: LoginId = requireNotNull(LoginId.of(input.loginId).get())

                val repository: UserCredentialRepository = mockk()
                val userCredential = UserCredential(
                    loginId = loginId,
                    userId = userId,
                    password = requireNotNull(Password.of("${input.password}-correct").get())
                )
                every { repository.findByLoginId(loginId) } returns userCredential

                val sut = createSut(
                    repository = repository,
                )

                val actual = sut.issue(input).getError() ?: fail("Unexpected success")
                UsecaseErrorsAssertion(
                    specErrors = setOf(UsecaseSpecErrorType.LOGIN_IS_FAIL),
                    usecaseErrors = actual
                )
                    .assertAll()
            }
        }
    }

    private fun createSut(
        repository: UserCredentialRepository = mockk(),
        eventHandler: AuthEventHandler = mockk(),
        clock: Clock = Clock.systemDefaultZone()
    ): IssueToken = IssueToken(
        repository = repository,
        eventHandler = eventHandler,
        clock = clock
    )

}
