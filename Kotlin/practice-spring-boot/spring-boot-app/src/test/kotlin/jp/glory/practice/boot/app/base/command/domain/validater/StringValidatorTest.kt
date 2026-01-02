package jp.glory.practice.boot.app.base.command.domain.validater

import com.github.michaelbull.result.getErrorOrElse
import com.github.michaelbull.result.getOrElse
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemErrorType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.regex.Pattern

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

            private fun assertError(actual: List<DomainItemErrorType>) {
                assertEquals(1, actual.size)
                assertEquals(DomainItemErrorType.REQUIRED, actual[0])
            }
        }
    }

    @Nested
    inner class ValidateMaxLength {
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
                val sut = createSut(value)
                sut.validateMaxLength(length = 5)
                val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

                assertError(actual.errors)
            }
        }

        private fun assertError(actual: List<DomainItemErrorType>) {
            assertEquals(1, actual.size)
            assertEquals(DomainItemErrorType.MAX_LENGTH, actual[0])
        }
    }

    @Nested
    inner class ValidateMinLength {
        @Test
        fun success() {
            val expected = "abcde"
            val sut = createSut(expected)
            sut.validateMinLength(length = 5)
            val actual = sut.parse { expected }.getOrElse { fail("Fail") }

            assertEquals(expected, actual)
        }

        @Nested
        inner class Fail {
            @Test
            fun whenOver() {
                val value = "abcd"
                val sut = createSut(value)
                sut.validateMinLength(length = 5)
                val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

                assertError(actual.errors)
            }
        }

        private fun assertError(actual: List<DomainItemErrorType>) {
            assertEquals(1, actual.size)
            assertEquals(DomainItemErrorType.MIN_LENGTH, actual[0])
        }
    }

    @Nested
    inner class ValidatePattern {
        private val pattern: Pattern = "[0-9]*".toPattern()

        @Test
        fun success() {
            val expected = "0123"
            val sut = createSut(expected)
            sut.validatePattern(pattern)
            val actual = sut.parse { expected }.getOrElse { fail("Fail") }

            assertEquals(expected, actual)
        }


        @Test
        fun fail() {
            val value = "abcdef"
            val sut = createSut(value)
            sut.validatePattern(pattern)
            val actual = sut.parse { value }.getErrorOrElse { fail("Fail") }

            assertError(actual.errors)
        }

        private fun assertError(actual: List<DomainItemErrorType>) {
            assertEquals(1, actual.size)
            assertEquals(DomainItemErrorType.FORMAT, actual[0])
        }
    }
}
