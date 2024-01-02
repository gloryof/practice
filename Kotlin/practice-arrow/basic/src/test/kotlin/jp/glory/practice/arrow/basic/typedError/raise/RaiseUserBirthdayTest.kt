package jp.glory.practice.arrow.basic.typedError.raise

import arrow.core.raise.fold
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.InvalidType
import jp.glory.practice.arrow.basic.typedError.raise.createUserBirthday
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class RaiseUserBirthdayTest {
    @Nested
    inner class TestCreate {
        @Nested
        inner class Valid {
            @Test
            fun valid() {
                val expected = LocalDate.of(1986, 12, 16)
                fold(
                    { createUserBirthday(expected) },
                    { fail("Must not return left") },
                    { actual -> Assertions.assertEquals(expected, actual.value) }
                )
            }

            @Test
            fun `when today`() {
                val expected = LocalDate.now()
                fold(
                    { createUserBirthday(expected) },
                    { fail("Must not return left") },
                    { actual -> Assertions.assertEquals(expected, actual.value) }
                )
            }
        }

        @Nested
        inner class Invalid {
            @Test
            fun `when future day`() {
                fold(
                    { createUserBirthday(LocalDate.now().plusDays(1)) },
                    { actual ->
                        val actualErrors = actual.getErrors()
                        Assertions.assertEquals(1, actualErrors.size)

                        val actualDetail = actualErrors[0]
                        Assertions.assertEquals(InvalidResource.Birthday, actualDetail.resource)
                        Assertions.assertEquals(InvalidType.FutureBirthday, actualDetail.type)
                    },
                    { fail("Must return ValidatedError") }
                )
            }

        }
    }
}