package jp.glory.practice.grpc.user.domain.model

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class UserIdTest {
    @Nested
    inner class CreateModel {

        @Nested
        inner class Valid {
            @Test
            fun valid() {
                assertDoesNotThrow {
                    UserId(UUID.randomUUID().toString())
                }
            }
        }

        @Nested
        inner class Invalid {
            @Test
            fun empty() {
                assertThrows<AssertionError> {
                    UserId("")
                }
            }

            @Test
            fun blank() {
                assertThrows<AssertionError> {
                    UserId("    ")
                }
            }
        }
    }
}