package jp.glory.practice.boot.app.base.command.domain.validater

import com.github.michaelbull.result.getErrorOrElse
import com.github.michaelbull.result.getOrElse
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemErrorType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate

class LocalDateValidatorTest {

    private fun createSut(value: LocalDate): LocalDateValidator {
        return LocalDateValidator(LocalDate::class, value)
    }

    @Nested
    inner class ValidateRequired {

        @Nested
        inner class Success {
            @Test
            fun whenIsBefore() {
                val expected = LocalDate.now()
                val sut = createSut(expected)
                sut.validateIsBeforeOrEquals(expected.plusDays(1))
                val actual = sut.parse { expected }.getOrElse { fail("fail") }

                assertEquals(expected, actual)
            }

            @Test
            fun whenSameDate() {
                val expected = LocalDate.now()
                val sut = createSut(expected)
                sut.validateIsBeforeOrEquals(expected)
                val actual = sut.parse { expected }.getOrElse { fail("fail") }

                assertEquals(expected, actual)
            }
        }

        @Nested
        inner class Fail {

            @Test
            fun whenIsAfter() {
                val value = LocalDate.now()
                val sut = createSut(value)
                sut.validateIsBeforeOrEquals(value.minusDays(1))
                val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

                assertError(actual.errors)
            }

            private fun assertError(actual: List<DomainItemErrorType>) {
                assertEquals(1, actual.size)
                assertEquals(DomainItemErrorType.DATE_IS_AFTER, actual[0])
            }
        }
    }

}
