package jp.glory.practice.arrow.basic.typedError

import arrow.core.Either
import arrow.core.getOrElse
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class EitherUserBirthdayTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun valid() {
                val expected = LocalDate.of(1986, 12, 16)
                val actual = EitherUserBirthday.create(expected)
                    .getOrElse { fail("Must not return left") }

                Assertions.assertEquals(expected, actual.value)
            }

            @Test
            fun `when today`() {
                val expected = LocalDate.now()
                val actual = EitherUserBirthday.create(expected)
                    .getOrElse { fail("Must not return left") }

                Assertions.assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Invalid {
            @Test
            fun `when future day`() {
                when (val actual = EitherUserBirthday.create(LocalDate.now().plusDays(1))) {
                    is Either.Left -> {
                        val actualErrors = actual.value.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.Birthday, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.FutureBirthday, actualDetail.type)
                    }

                    is Either.Right -> fail("Must return ValidatedError")
                }
            }

        }
    }
}