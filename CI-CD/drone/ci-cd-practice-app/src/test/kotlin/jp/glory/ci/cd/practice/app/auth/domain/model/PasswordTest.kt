package jp.glory.ci.cd.practice.app.auth.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PasswordTest {

    @Nested
    inner class Initialization {
        @Test
        fun success() {
            assertDoesNotThrow {
                Password("12345678901234567890")
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun emptyPattern() {
                assertThrows<IllegalArgumentException> {
                    Password("")
                }
            }
            @Test
            fun notSatisfyLength() {
                assertThrows<IllegalArgumentException> {
                    Password("1234567890123456789")
                }
            }
        }
    }

    @Nested
    inner class Match {
        @Test
        fun matchPattern() {
            val sut = Password("test-123456789abcdef-0001")
            assertTrue(sut.match(Password("test-123456789abcdef-0001")))
        }
        @Test
        fun notMatchPattern() {
            val sut = Password("test-123456789abcdef-0001")
            assertFalse(sut.match(Password("test-123456789abcdef-0003")))
            assertFalse(sut.match(Password("test-123456789abcdef-0002")))
        }
    }
}