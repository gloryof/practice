package jp.glory.practice.boot.app.user.command.domain.service

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.every
import io.mockk.mockk
import jp.glory.practice.boot.app.auth.command.domain.model.LoginId
import jp.glory.practice.boot.app.base.command.domain.exception.DomainSpecErrorType
import jp.glory.practice.boot.app.test.tool.DomainErrorsAssertion
import jp.glory.practice.boot.app.user.command.domain.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class UserServiceTest {
    @Nested
    inner class TestValidateExistLogin {
        @Test
        fun whenNotExistErrorIsNotOccurred() {
            val loginId = LoginId.of("test-login-id").get() ?: fail()
            val repository: UserRepository = mockk()
            every { repository.existLoginId(loginId) } returns false

            val sut = createSut(repository)
            val actual = sut.validateExistLogin(loginId)

            assertNotNull(actual.get())
        }

        @Test
        fun whenExistErrorIsOccurred() {
            val loginId = LoginId.of("test-login-id").get() ?: fail()
            val repository: UserRepository = mockk()
            every { repository.existLoginId(loginId) } returns true

            val sut = createSut(repository)
            val actual = sut.validateExistLogin(loginId)

            val error = actual.getError() ?: fail("Error is null")

            val assertion = DomainErrorsAssertion(
                specErrors = setOf(DomainSpecErrorType.USER_ID_ALREADY_EXIST),
                domainErrors = error
            )
            assertion.assertAll()
        }
    }

    private fun createSut(
        repository: UserRepository
    ): UserService = UserService(
        repository = repository
    )

}
