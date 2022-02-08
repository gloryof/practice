package jp.glory.ci.cd.practice.app.auth.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CredentialUserIdTest {

    @Nested
    inner class Initialization {
        @Test
        fun success() {
            assertDoesNotThrow {
                CredentialUserId("test1234")
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun emptyPattern() {
                assertThrows<IllegalArgumentException> {
                    CredentialUserId("")
                }
            }
        }
    }

    @Nested
    inner class Match {
        @Test
        fun matchPattern() {
            val sut = CredentialUserId("test")
            assertTrue(sut.match(CredentialUserId("test")))
        }

        @Test
        fun notMatchPattern() {
            val sut = CredentialUserId("test")
            assertFalse(sut.match(CredentialUserId("test2")))
            assertFalse(sut.match(CredentialUserId("test3")))
        }
    }
}