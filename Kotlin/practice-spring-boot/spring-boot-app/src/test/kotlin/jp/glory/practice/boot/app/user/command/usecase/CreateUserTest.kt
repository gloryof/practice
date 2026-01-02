package jp.glory.practice.boot.app.user.command.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.exception.DomainSpecErrorType
import jp.glory.practice.boot.app.test.tool.UsecaseErrorsAssertion
import jp.glory.practice.boot.app.user.command.domain.event.UserCreated
import jp.glory.practice.boot.app.user.command.domain.event.UserEventHandler
import jp.glory.practice.boot.app.user.command.domain.model.UserId
import jp.glory.practice.boot.app.user.command.domain.model.UserIdGenerator
import jp.glory.practice.boot.app.user.command.domain.service.UserService
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.Clock
import java.time.LocalDate
import kotlin.test.assertEquals

class CreateUserTest {
    @Nested
    inner class TestCreateUser {
        @Test
        fun success() {
            val input = CreateUser.Input(
                loginId = "test-login-id",
                userName = "test-user-name",
                password = "test-password12345",
                birthday = LocalDate.of(1980, 1, 1),
            )

            val clock = Clock.systemUTC()

            val userId = UserId("test-user-id")
            val userIdGenerator: UserIdGenerator = mockk()
            every { userIdGenerator.issueNewId() } returns userId

            val userService: UserService = mockk()
            val loginId: LoginId = LoginId.of(input.loginId).get() ?: fail("Login ID is invalid")
            every { userService.validateExistLogin(loginId) } returns Ok(Unit)

            val userEventHandler: UserEventHandler = mockk()
            val slot = slot<UserCreated>()
            every { userEventHandler.handleUserCreated(capture(slot)) } just Runs

            val sut = createSut(
                clock = clock,
                userService = userService,
                userEventHandler = userEventHandler,
                userIdGenerator = userIdGenerator
            )
            val actual = sut.createUser(input).get() ?: fail("Error is occurred")

            val expected: UserCreated = UserCreated.create(
                inputLoginId = input.loginId,
                inputUserName = input.userName,
                inputPassword = input.password,
                inputBirthday = input.birthday,
                today = LocalDate.now(clock),
                userIdGenerator = userIdGenerator
            ).get() ?: fail("Invalid parameter")
            assertEquals(userId.value, actual)

            val actualCreated = slot.captured
            assertEquals(expected.userId.value, actualCreated.userId.value)
            assertEquals(expected.loginId.value, actualCreated.loginId.value)
            assertEquals(expected.userName.value, actualCreated.userName.value)
            assertEquals(expected.password.value, actualCreated.password.value)
            assertEquals(expected.birthday.value, actualCreated.birthday.value)
        }

        @Nested
        inner class Fail {

            @Test
            fun whenExistLoginId() {
                val input = CreateUser.Input(
                    loginId = "test-login-id",
                    userName = "test-user-name",
                    password = "test-password",
                    birthday = LocalDate.of(1980, 1, 1),
                )

                val clock = Clock.systemUTC()

                val userId = UserId("test-user-id")
                val userIdGenerator: UserIdGenerator = mockk()
                every { userIdGenerator.issueNewId() } returns userId

                val userService: UserService = mockk()
                val loginId: LoginId = LoginId.of(input.loginId).get() ?: fail("Login ID is invalid")
                every { userService.validateExistLogin(loginId) } returns Err(
                    DomainErrors(
                        specErrors = listOf(DomainSpecErrorType.USER_ID_ALREADY_EXIST)
                    )
                )

                val sut = createSut(
                    clock = clock,
                    userService = userService,
                    userIdGenerator = userIdGenerator
                )
                val actual = sut.createUser(input).getError() ?: fail("Error is not occurred")
                val assertion = UsecaseErrorsAssertion(
                    names = emptySet(),
                    usecaseErrors = actual
                )
                assertion.assertAll()
            }

            @Test
            fun whenAnyInputIsInvalid() {
                val input = CreateUser.Input(
                    loginId = "",
                    userName = "test-user-name",
                    password = "test-password",
                    birthday = LocalDate.of(1980, 1, 1),
                )

                val clock = Clock.systemUTC()

                val userId = UserId("test-user-id")
                val userIdGenerator: UserIdGenerator = mockk()
                every { userIdGenerator.issueNewId() } returns userId

                val sut = createSut(
                    clock = clock,
                    userIdGenerator = userIdGenerator
                )
                val actual = sut.createUser(input).getError() ?: fail("Error is not occurred")
                val assertion = UsecaseErrorsAssertion(
                    names = emptySet(),
                    usecaseErrors = actual
                )
                assertion.assertAnyItemError()
            }
        }
    }

    private fun createSut(
        clock: Clock = Clock.systemUTC(),
        userService: UserService = mockk(),
        userEventHandler: UserEventHandler = mockk(),
        userIdGenerator: UserIdGenerator = mockk()
    ): CreateUser = CreateUser(
        clock = clock,
        userService = userService,
        userEventHandler = userEventHandler,
        userIdGenerator = userIdGenerator
    )
}
