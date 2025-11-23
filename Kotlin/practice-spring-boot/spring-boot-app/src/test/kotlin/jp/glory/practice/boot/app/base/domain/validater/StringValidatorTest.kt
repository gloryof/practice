package jp.glory.practice.boot.app.base.domain.validater

import com.github.michaelbull.result.getErrorOrElse
import com.github.michaelbull.result.getOrElse
import jp.glory.practice.boot.app.base.domain.exception.DomainError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class StringValidatorTest {

    private fun createSut(value: String): StringValidator {
        return StringValidator(String::class, value)
    }

    @Nested
    inner class ValidateRequired {

        @Test
        fun success() {
            val expected = "testValue"
            val sut = createSut(expected)
            sut.validateRequired()
            val actual = sut.parse { expected }.getOrElse { fail("fail") }

            assertEquals(expected, actual)
        }

        @Nested
        inner class Fail {

            @Test
            fun whenEmpty() {
                val value = ""
                val sut = createSut(value)
                sut.validateRequired()
                val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

                assertError(actual.errors)
            }

            @Test
            fun whenSpaceOnly() {
                val value = "  "
                val sut = createSut(value)
                sut.validateRequired()
                val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

                assertError(actual.errors)
            }

            private fun assertError(actual: List<DomainError>) {
                assertEquals(1, actual.size)
                assertEquals(DomainError.REQUIRED, actual[0])
            }
        }
    }

    @Nested
    inner class MaxLength {
        @Test
        fun success() {
            val expected = "abcde"
            val sut = createSut(expected)
            sut.validateMaxLength(length = 5)
            val actual = sut.parse { expected }.getOrElse { fail("Fail") }

            assertEquals(expected, actual)
        }

        @Nested
        inner class Fail {
            @Test
            fun whenOver() {
                val value = "abcdef"
                val sut = createSut("abcdef")
                sut.validateMaxLength(length = 5)
                val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

                assertError(actual.errors)
            }
        }

        private fun assertError(actual: List<DomainError>) {
            assertEquals(1, actual.size)
            assertEquals(DomainError.MAX_LENGTH, actual[0])
        }
    }

}
