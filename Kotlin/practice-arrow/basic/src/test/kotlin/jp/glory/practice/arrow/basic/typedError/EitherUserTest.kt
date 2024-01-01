package jp.glory.practice.arrow.basic.typedError

import arrow.core.Either
import arrow.core.getOrElse
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import jp.glory.practice.arrow.basic.typedError.common.TypedUserStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class EitherUserTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun success() {
                val expectedUserName = "test-user"
                val expectedBirthday = LocalDate.of(1986, 12, 16)
                val actual = EitherUser.create(
                    userName = expectedUserName,
                    birthday =  expectedBirthday
                )
                    .getOrElse { fail("Must not ") }

                Assertions.assertTrue(actual.userId.value.isNotEmpty())
                Assertions.assertEquals(expectedUserName, actual.userName.value)
                Assertions.assertEquals(expectedBirthday, actual.birthday.value)
                Assertions.assertEquals(TypedUserStatus.Active, actual.status)
            }

            @Test
            fun `when threshold input`() {
                val expectedUserName = "a".repeat(100)
                val expectedBirthday = LocalDate.now()
                val actual = EitherUser.create(
                    userName = expectedUserName,
                    birthday =  expectedBirthday
                )
                    .getOrElse { fail("Must not ") }

                Assertions.assertTrue(actual.userId.value.isNotEmpty())
                Assertions.assertEquals(expectedUserName, actual.userName.value)
                Assertions.assertEquals(expectedBirthday, actual.birthday.value)
                Assertions.assertEquals(TypedUserStatus.Active, actual.status)
            }
        }

        @Nested
        inner class Invalid {

            @Test
            fun `when user name is invalid`() {
                val expectedUserName = ""
                val expectedBirthday = LocalDate.of(1986, 12, 16)
                val actual = EitherUser.create(
                    userName = expectedUserName,
                    birthday =  expectedBirthday
                )

                when (actual) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()

                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    }
                    is Either.Right -> fail("Must return left")
                }
            }

            @Test
            fun `when birthday is invalid`() {
                val expectedUserName = "test"
                val expectedBirthday = LocalDate.now().plusDays(1)
                val actual = EitherUser.create(
                    userName = expectedUserName,
                    birthday =  expectedBirthday
                )

                when (actual) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()

                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.Birthday, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.FutureBirthday, actualDetail.type)
                    }
                    is Either.Right -> fail("Must return left")
                }
            }

            @Test
            fun `when all is invalid`() {
                val expectedUserName = ""
                val expectedBirthday = LocalDate.now().plusDays(1)
                val actual = EitherUser.create(
                    userName = expectedUserName,
                    birthday =  expectedBirthday
                )

                when (actual) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()

                        Assertions.assertEquals(2, actualErrors.size)

                        val actualDetail1 = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail1.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail1.type)

                        val actualDetail2 = actualErrors[1]
                        Assertions.assertEquals(InvalidResource.Birthday, actualDetail2.resource)
                        Assertions.assertEquals(InvalidType.FutureBirthday, actualDetail2.type)
                    }
                    is Either.Right -> fail("Must return left")
                }
            }
        }
    }
}