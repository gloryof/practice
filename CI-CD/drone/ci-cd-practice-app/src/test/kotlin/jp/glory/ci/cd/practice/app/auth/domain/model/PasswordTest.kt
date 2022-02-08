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
                Password("test1234")
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
        }
    }

    @Nested
    inner class Match {
        @Test
        fun matchPattern() {
            val sut = Password("test")
            assertTrue(sut.match(Password("test")))
        }
        @Test
        fun notMatchPattern() {
            val sut = Password("test")
            assertFalse(sut.match(Password("test2")))
            assertFalse(sut.match(Password("test3")))
        }
    }
}