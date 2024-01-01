package jp.glory.practice.arrow.basic.typedError

import arrow.core.Either
import arrow.core.getOrElse
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class EitherUserIdTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun valid() {
                val expected = "test-user-uid"
                val actual = EitherUserId.create(expected)
                    .getOrElse { fail("Must not return left") }

                Assertions.assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Invalid {
            @Test
            fun `when empty`() {
                when (val actual = EitherUserId.create("")) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserId, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    }

                    is Either.Right -> fail("Must return ValidatedError")
                }
            }

            @Test
            fun `when blank`() {
                when (val actual = EitherUserId.create("   ")) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.UserId, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.Required, actualDetail.type)
                    }

                    is Either.Right -> fail("Must return ValidatedError")
                }
            }
        }
    }
}