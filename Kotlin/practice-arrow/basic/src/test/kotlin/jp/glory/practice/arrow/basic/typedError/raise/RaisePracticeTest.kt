package jp.glory.practice.arrow.basic.typedError.raise

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.fold
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import jp.glory.practice.arrow.basic.typedError.common.UserNotFoundError
import jp.glory.practice.arrow.basic.typedError.common.ValidatedError
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class RaisePracticeTest {
    @Nested
    inner class TestRegister {
        @Test
        fun success() {
            val input = RaiseRegisterInput(
                userName = "test-user",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = RaiseUserRepositoryImpl()

            fold(
                { registerUser(repository, input) },
                { fail("Register is failed") },
                { actual -> Assertions.assertTrue(actual.value.isNotBlank()) }
            )
        }


        @Test
        fun `when invalid input`() {
            val input = RaiseRegisterInput(
                userName = "",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = RaiseUserRepositoryImpl()

            fold(
                { registerUser(repository, input) },
                { actual ->
                    val actualResult = actual as ValidatedError
                    val actualErrors = actualResult.getErrors()

                    Assertions.assertEquals(1, actualErrors.size)

                    val actualDetail = actualErrors[0]
                    Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                    Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                },
                { fail("Must return left") }
            )
        }
    }

    @Nested
    inner class TestFindById {
        @Test
        fun success() {
            val targetUser = RaiseUserRepositoryImpl.activeUser
            val repository = RaiseUserRepositoryImpl()
                .also { it.save(targetUser) }

            fold(
                { findById(repository, targetUser.userId) },
                { fail("Not found user") },
                { actual ->
                    Assertions.assertEquals(targetUser.userId, actual.userId)
                    Assertions.assertEquals(targetUser.userName, actual.userName)
                }
            )
        }

        @Test
        fun notFound() {
            val repository = RaiseUserRepositoryImpl()

            fold(
                { findById(repository, RaiseUserId.generate()) },
                { actual ->
                    Assertions.assertEquals(UserNotFoundError, actual)
                },
                { fail("Must return UserNotFound") },
            )

        }
    }
}