package jp.glory.practice.arrow.basic.typedError.raise

import arrow.core.raise.fold
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import jp.glory.practice.arrow.basic.typedError.common.TypedUserStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class RaiseUserTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun success() {
                val expectedUserName = "test-user"
                val expectedBirthday = LocalDate.of(1986, 12, 16)
                fold(
                    {
                        createUser(
                            userName = expectedUserName,
                            birthday = expectedBirthday
                        )
                    },
                    { fail("Must not return left") },
                    { actual ->
                        Assertions.assertTrue(actual.userId.value.isNotEmpty())
                        Assertions.assertEquals(expectedUserName, actual.userName.value)
                        Assertions.assertEquals(expectedBirthday, actual.birthday.value)
                        Assertions.assertEquals(TypedUserStatus.Active, actual.status)
                    }
                )
            }

            @Test
            fun `when threshold input`() {
                val expectedUserName = "a".repeat(100)
                val expectedBirthday = LocalDate.now()
                fold(
                    {
                        createUser(
                            userName = expectedUserName,
                            birthday = expectedBirthday
                        )
                    },
                    { fail("Must not return left") },
                    { actual ->
                        Assertions.assertTrue(actual.userId.value.isNotEmpty())
                        Assertions.assertEquals(expectedUserName, actual.userName.value)
                        Assertions.assertEquals(expectedBirthday, actual.birthday.value)
                        Assertions.assertEquals(TypedUserStatus.Active, actual.status)
                    }
                )
            }
        }

        @Nested
        inner class Invalid {

            @Test
            fun `when user name is invalid`() {
                val expectedUserName = ""
                val expectedBirthday = LocalDate.of(1986, 12, 16)
                fold(
                    {
                        createUser(
                            userName = expectedUserName,
                            birthday = expectedBirthday
                        )
                    },
                    { actual ->
                        val actualErrors = actual.getErrors()

                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    },
                    { fail("Must return left")  }
                )
            }

            @Test
            fun `when birthday is invalid`() {
                val expectedUserName = "test"
                val expectedBirthday = LocalDate.now().plusDays(1)
                fold(
                    {
                        createUser(
                            userName = expectedUserName,
                            birthday = expectedBirthday
                        )
                    },
                    { actual ->
                        val actualErrors = actual.getErrors()

                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.Birthday, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.FutureBirthday, actualDetail.type)
                    },
                    { fail("Must return left")  }
                )
            }

            @Test
            fun `when all is invalid`() {
                val expectedUserName = ""
                val expectedBirthday = LocalDate.now().plusDays(1)
                fold(
                    {
                        createUser(
                            userName = expectedUserName,
                            birthday = expectedBirthday
                        )
                    },
                    { actual ->
                        val actualErrors = actual.getErrors()

                        Assertions.assertEquals(2, actualErrors.size)

                        val actualDetail1 = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail1.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail1.type)

                        val actualDetail2 = actualErrors[1]
                        Assertions.assertEquals(InvalidResource.Birthday, actualDetail2.resource)
                        Assertions.assertEquals(InvalidType.FutureBirthday, actualDetail2.type)
                    },
                    { fail("Must return left")  }
                )
            }
        }
    }
}