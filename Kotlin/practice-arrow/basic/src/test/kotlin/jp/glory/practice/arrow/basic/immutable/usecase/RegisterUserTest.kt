package jp.glory.practice.arrow.basic.immutable.usecase

import jp.glory.practice.arrow.basic.immutable.model.UserRepositoryImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class RegisterUserTest {

    @Nested
    inner class TestRegister {
        @Test
        fun success() {
            val input = RegisterUser.Input(
                userName = "test-user",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = UserRepositoryImpl()

            val sut = RegisterUser(repository)

            val actual = sut.register(input)

            Assertions.assertTrue(actual.value.isNotBlank())
        }

        @Test
        fun `when invalid input`() {
            val input = RegisterUser.Input(
                userName = "",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = UserRepositoryImpl()

            val sut = RegisterUser(repository)

            assertThrows<AssertionError> {
                sut.register(input)
            }
        }
    }

}