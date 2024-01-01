package jp.glory.practice.arrow.basic.typedError

import arrow.core.Either
import arrow.core.getOrElse
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class EitherUserNameTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun valid() {
                val expected = "test-user-name"
                val actual = EitherUserName.create(expected)
                    .getOrElse { fail("Must not return left") }

                Assertions.assertEquals(expected, actual.value)
            }

            @Test
            fun `Equals max length`() {
                val expected = "a".repeat(100)
                val actual = EitherUserName.create(expected)
                    .getOrElse { fail("Must not return left") }

                Assertions.assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Invalid {

            @Test
            fun `when empty`() {
                when (val actual = EitherUserName.create("")) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    }

                    is Either.Right -> fail("Must return ValidatedError")
                }
            }

            @Test
            fun `when blank`() {
                when (val actual = EitherUserName.create("   ")) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    }

                    is Either.Right -> fail("Must return ValidatedError")
                }
            }

            @Test
            fun `when max length over`() {
                when (val actual = EitherUserName.create("a".repeat(101))) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserName, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.MaxLengthOver, actualDetail.type)
                    }

                    is Either.Right -> fail("Must return ValidatedError")
                }
            }
        }
    }
}