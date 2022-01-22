package jp.glory.ci.cd.practice.app.user.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GivenNameTest {

    @Nested
    inner class TestInitialize {
        private val maxLength = 50

        @Test
        fun validValue() {
            assertDoesNotThrow {
                GivenName("a".repeat(maxLength))
                GivenName("あ".repeat(maxLength))
                GivenName("!".repeat(maxLength))
            }
        }

        @Test
        fun emptyIsInvalid() {
            assertThrows<IllegalArgumentException> {
                GivenName("")
            }
        }

        @Test
        fun overMaxLength() {
            assertThrows<IllegalArgumentException> {
                GivenName("a".repeat(maxLength + 1))
            }
        }
    }
}