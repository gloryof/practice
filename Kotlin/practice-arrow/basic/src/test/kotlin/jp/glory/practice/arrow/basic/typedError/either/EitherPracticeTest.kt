package jp.glory.practice.arrow.basic.typedError.either

import arrow.core.Either
import arrow.core.getOrElse
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import jp.glory.practice.arrow.basic.typedError.common.UserNotFoundError
import jp.glory.practice.arrow.basic.typedError.common.ValidatedError
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class EitherPracticeTest {
    @Nested
    inner class TestRegister {
        @Test
        fun success() {
            val input = EitherPractice.RegisterInput(
                userName = "test-user",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = EitherUserRepositoryImpl()

            val sut = EitherPractice(repository)

            val actual = sut.register(input)
                .getOrElse { fail("Register is failed") }

            Assertions.assertTrue(actual.value.isNotBlank())
        }


        @Test
        fun `when invalid input`() {
            val input = EitherPractice.RegisterInput(
                userName = "",
                birthday = LocalDate.of(1986, 12, 16)
            )
            val repository = EitherUserRepositoryImpl()
            val sut = EitherPractice(repository)

            when (val actual = sut.register(input)) {
                is Either.Left -> {
                    val actualResult = actual.value as ValidatedError
                    val actualErrors = actualResult.getErrors()

                    Assertions.assertEquals(1, actualErrors.size)

                    val actualDetail = actualErrors[0]
                    Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                    Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                }
                is Either.Right -> fail("Must return left")
            }
        }
    }

    @Nested
    inner class TestFindById {
        @Test
        fun success() {
            val targetUser = EitherUserRepositoryImpl.activeUser
            val repository = EitherUserRepositoryImpl()
                .also { it.save(targetUser) }

            val sut = EitherPractice(repository)

            val actual = sut.findById(targetUser.userId)
                .getOrElse { fail("Not found user") }

            Assertions.assertEquals(targetUser.userId, actual.userId)
            Assertions.assertEquals(targetUser.userName, actual.userName)
        }

        @Test
        fun notFound() {
            val repository = EitherUserRepositoryImpl()
            val sut = EitherPractice(repository)

            when (val actual = sut.findById(EitherUserId.generate())) {
                is Either.Left -> {
                    val actualError = actual.value
                    Assertions.assertEquals(UserNotFoundError, actualError)
                }
                is Either.Right ->  fail("Must return UserNotFound")
            }

        }
    }
}