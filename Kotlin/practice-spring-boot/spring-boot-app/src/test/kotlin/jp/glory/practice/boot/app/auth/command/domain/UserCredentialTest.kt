package jp.glory.practice.boot.app.auth.command.domain

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.auth.command.domain.model.Password
import jp.glory.practice.boot.app.auth.command.domain.model.UserCredential
import jp.glory.practice.boot.app.base.domain.exception.DomainSpecErrorType
import jp.glory.practice.boot.app.user.command.domain.model.UserId
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.fail
import java.time.Clock
import kotlin.test.Test
import kotlin.test.assertEquals

class UserCredentialTest {

    @Nested
    inner class TestIssueToken {
        @Test
        fun success() {
            val password = requireNotNull(Password.of("test-password-123456").get())
            val sut = createSut(
                password = password
            )

            val actual = sut.issueToken(
                inputPassword = password.value,
                clock = Clock.systemDefaultZone()
            ).get() ?: fail("Invalid password")
            assertEquals(sut.userId.value, actual.userId.value)
        }

        @Test
        fun failWhenPasswordInvalid() {
            val password = requireNotNull(Password.of("test-password-123456").get())
            val sut = createSut(
                password = password
            )

            val actual = sut.issueToken(
                inputPassword = "wrong",
                clock = Clock.systemDefaultZone()
            ).getError() ?: fail("Unexpected issued token")

            assertEquals(DomainSpecErrorType.AUTHENTICATED_IS_FAIL, actual)
        }
    }

    private fun createSut(
        loginId: LoginId = requireNotNull(LoginId.of("test-login-id").get()),
        userId: UserId = UserId("test-user-id"),
        password: Password = requireNotNull(Password.of("test-password-123456").get())
    ): UserCredential =
        UserCredential(
            loginId = loginId,
            userId = userId,
            password = password
        )
}
