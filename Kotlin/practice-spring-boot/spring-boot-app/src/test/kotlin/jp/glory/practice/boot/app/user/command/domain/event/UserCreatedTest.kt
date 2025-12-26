package jp.glory.practice.boot.app.user.command.domain.event

import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOrThrow
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.test.tool.DomainErrorsAssertion
import jp.glory.practice.boot.app.user.command.domain.model.Birthday
import jp.glory.practice.boot.app.user.command.domain.model.LoginId
import jp.glory.practice.boot.app.user.command.domain.model.Password
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.command.domain.model.UserName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.fail

class UserCreatedTest {

    @Nested
    inner class Of {
        private val today = LocalDate.now()

        @Test
        fun success() {
            val input = Input()
            val actual = callAndGetResult(
                input = input,
                today = today,
                userIdGenerator = UserIdGenerator()
            )

            assertTrue(actual.userId.value.isNotEmpty())
            assertEquals(input.loginId, actual.loginId.value)
            assertEquals(input.userName, actual.userName.value)
            assertEquals(input.password, actual.password.value)
            assertEquals(input.birthday, actual.birthday.value)
        }

        @Nested
        inner class Fail {
            @Test
            fun whenInvalidLoginId() {
                val input = Input(
                    loginId = ""
                )

                val actual = callAndGetError(
                    input = input,
                    today = today
                )

                val assertion = DomainErrorsAssertion(
                    names = setOf(LoginId::class),
                    domainErrors = actual
                )
                assertion.assertAll()
            }

            @Test
            fun whenInvalidUserName() {
                val input = Input(
                    userName = ""
                )

                val actual = callAndGetError(
                    input = input,
                    today = today
                )

                val assertion = DomainErrorsAssertion(
                    names = setOf(UserName::class),
                    domainErrors = actual
                )
                assertion.assertAll()
            }

            @Test
            fun whenInvalidPassword() {
                val input = Input(
                    password = ""
                )

                val actual = callAndGetError(
                    input = input,
                    today = today
                )

                val assertion = DomainErrorsAssertion(
                    names = setOf(Password::class),
                    domainErrors = actual
                )
                assertion.assertAll()
            }

            @Test
            fun whenInvalidBirthday() {
                val input = Input(
                    birthday = LocalDate.MAX
                )

                val actual = callAndGetError(
                    input = input,
                    today = today
                )

                val assertion = DomainErrorsAssertion(
                    names = setOf(Birthday::class),
                    domainErrors = actual
                )
                assertion.assertAll()
            }

            @Test
            fun whenInvalidAll() {
                val input = Input(
                    loginId = "",
                    userName = "",
                    password = "",
                    birthday = LocalDate.MAX
                )

                val actual = callAndGetError(
                    input = input,
                    today = today
                )

                val assertion = DomainErrorsAssertion(
                    names = setOf(
                        LoginId::class,
                        UserName::class,
                        Password::class,
                        Birthday::class
                    ),
                    domainErrors = actual
                )
                assertion.assertAll()
            }
        }

        private fun callAndGetResult(
            input: Input,
            today: LocalDate,
            userIdGenerator: UserIdGenerator
        ): UserCreated =
            UserCreated.create(
                inputLoginId = input.loginId,
                inputUserName = input.userName,
                inputPassword = input.password,
                inputBirthday = input.birthday,
                today = today,
                userIdGenerator = userIdGenerator
            ).getOrThrow { fail("Fail") }

        private fun callAndGetError(
            input: Input,
            today: LocalDate
        ): DomainErrors {
            val error = UserCreated.create(
                inputLoginId = input.loginId,
                inputUserName = input.userName,
                inputPassword = input.password,
                inputBirthday = input.birthday,
                today = today,
                userIdGenerator = UserIdGenerator()
            ).getError()

            return error ?: fail("Error is null")
        }

        inner class Input(
            var loginId: String = "test-login-id",
            var userName: String = "test-user-name",
            var password: String = "test-password-min",
            var birthday: LocalDate = LocalDate.of(1980, 1, 1),
        )
    }

}
