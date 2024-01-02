package jp.glory.practice.arrow.basic.typedError.raise

import arrow.core.raise.fold
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class RaiseUserNameTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun valid() {
                val expected = "test-user-name"
                fold(
                    { createUserName(expected) },
                    { fail("Must not return left") },
                    { actual -> Assertions.assertEquals(expected, actual.value) }
                )
            }

            @Test
            fun `Equals max length`() {
                val expected = "a".repeat(100)
                fold(
                    { createUserName(expected) },
                    { fail("Must not return left") },
                    { actual -> Assertions.assertEquals(expected, actual.value) }
                )
            }
        }

        @Nested
        inner class Invalid {

            @Test
            fun `when empty`() {
                fold(
                    { createUserName("") },
                    { actual ->
                        val actualErrors = actual.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    },
                    { fail("Must return ValidatedError") }
                )
            }

            @Test
            fun `when blank`() {
                fold(
                    { createUserName("     ") },
                    { actual ->
                        val actualErrors = actual.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    },
                    { fail("Must return ValidatedError") }
                )
            }

            @Test
            fun `when max length over`() {
                fold(
                    { createUserName("a".repeat(101)) },
                    { actual ->
                        val actualErrors = actual.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.MaxLengthOver, actualDetail.type)
                    },
                    { fail("Must return ValidatedError") }
                )
            }
        }
    }
}