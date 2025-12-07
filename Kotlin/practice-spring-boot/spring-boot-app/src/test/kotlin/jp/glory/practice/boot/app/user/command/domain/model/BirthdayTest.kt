package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOrThrow
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.test.tool.DomainErrorAssertion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import java.time.LocalDate
import kotlin.test.fail

class BirthdayTest {

    @Nested
    inner class Of {
        @Nested
        inner class Success {
            @Test
            fun whenIsBefore() {
                val today = LocalDate.now()
                val value = today.minusDays(1)

                val actual = Birthday.Companion.of(value, today).getOrThrow { fail("Fail") }
                assertEquals(value, actual.value)
            }

            @Test
            fun whenSameDay() {
                val today = LocalDate.now()
                val value = today

                val actual = Birthday.Companion.of(value, today).getOrThrow { fail("Fail") }
                assertEquals(value, actual.value)
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun whenIsAfter() {
                val today = LocalDate.now()
                val value = today.plusDays(1)
                val actual = Birthday.Companion.of(value, today).getError()

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertRequired()
            }

            private fun createErrorAssertion(actual: DomainErrors): DomainErrorAssertion =
                DomainErrorAssertion(
                    name = requireNotNull(Birthday::class.simpleName),
                    actual = actual
                )

        }
    }
}
