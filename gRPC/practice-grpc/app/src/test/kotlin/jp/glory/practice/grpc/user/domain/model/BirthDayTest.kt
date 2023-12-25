package jp.glory.practice.grpc.user.domain.model

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class BirthDayTest {
    @Nested
    inner class CreateModel {

        @Nested
        inner class Valid {
            @Test
            fun valid() {
                assertDoesNotThrow {
                    Birthday(LocalDate.of(1986, 12, 16))
                }
            }
        }

        @Nested
        inner class Invalid {
            @Test
            fun futureDay() {
                assertThrows<AssertionError> {
                    Birthday(LocalDate.now().plusDays(1))
                }
            }
        }
    }
}