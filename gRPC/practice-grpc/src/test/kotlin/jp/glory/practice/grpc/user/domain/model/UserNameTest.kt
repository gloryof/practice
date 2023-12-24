package jp.glory.practice.grpc.user.domain.model

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class UserNameTest {
    @Nested
    inner class CreateModel {

        @Nested
        inner class Valid {
            @Test
            fun alphabetOnly() {
                assertDoesNotThrow {
                    UserName("test-user")
                }
            }
            @Test
            fun multibyte() {
                assertDoesNotThrow {
                    UserName("テストユーザ")
                }
            }

            @Test
            fun maxLength() {
                assertDoesNotThrow {
                    UserName("a".repeat(100))
                }
            }
        }

        @Nested
        inner class Invalid {
            @Test
            fun empty() {
                assertThrows<AssertionError> {
                    UserName("")
                }
            }

            @Test
            fun blank() {
                assertThrows<AssertionError> {
                    UserName("    ")
                }
            }


            @Test
            fun maxLengthOver() {
                assertThrows<AssertionError> {
                    UserName("a".repeat(101))
                }
            }
        }
    }
}