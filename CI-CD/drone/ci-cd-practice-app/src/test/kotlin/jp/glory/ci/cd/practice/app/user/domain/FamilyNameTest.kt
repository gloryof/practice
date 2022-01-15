package jp.glory.ci.cd.practice.app.user.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FamilyNameTest {

    @Nested
    inner class TestInitialize {
        private val MAX_LENGHT = 50

        @Test
        fun validValue() {
            assertDoesNotThrow {
                FamilyName("a".repeat(MAX_LENGHT))
                FamilyName("あ".repeat(MAX_LENGHT))
                FamilyName("!".repeat(MAX_LENGHT))
            }
        }

        @Test
        fun emptyIsInvalid() {
            assertThrows<IllegalArgumentException> {
                FamilyName("")
            }
        }

        @Test
        fun overMaxLength() {
            assertThrows<IllegalArgumentException> {
                FamilyName("a".repeat(MAX_LENGHT + 1))
            }
        }
    }
}