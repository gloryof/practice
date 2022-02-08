package jp.glory.ci.cd.practice.app.user.domain

import jp.glory.ci.cd.practice.app.user.domain.model.FamilyName
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FamilyNameTest {

    @Nested
    inner class TestInitialize {
        private val maxLength = 50

        @Test
        fun validValue() {
            assertDoesNotThrow {
                FamilyName("a".repeat(maxLength))
                FamilyName("あ".repeat(maxLength))
                FamilyName("!".repeat(maxLength))
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
                FamilyName("a".repeat(maxLength + 1))
            }
        }
    }
}