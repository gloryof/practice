package jp.glory.ci.cd.practice.app.user.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GivenNameTest {

    @Nested
    inner class TestInitialize {
        private val MAX_LENGHT = 50

        @Test
        fun validValue() {
            assertDoesNotThrow {
                GivenName("a".repeat(MAX_LENGHT))
                GivenName("あ".repeat(MAX_LENGHT))
                GivenName("!".repeat(MAX_LENGHT))
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
                GivenName("a".repeat(MAX_LENGHT + 1))
            }
        }
    }
}