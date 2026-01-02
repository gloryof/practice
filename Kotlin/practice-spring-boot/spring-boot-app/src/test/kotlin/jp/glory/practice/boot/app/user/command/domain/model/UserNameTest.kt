package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOrThrow
import jp.glory.practice.boot.app.base.command.domain.exception.DomainItemError
import jp.glory.practice.boot.app.test.tool.DomainErrorAssertion
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.fail

class UserNameTest {
    @Nested
    inner class Of {
        @Nested
        inner class Success {
            @Test
            fun normal() {
                val expected = "test"
                val actual = UserName.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun maxLength() {
                val expected = "あ".repeat(UserName.Companion.MAX_LENGTH)
                val actual = UserName.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun whenEmpty() {
                val actual = UserName.Companion.of("").getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    required = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }

            @Test
            fun whenSpaceOnly() {
                val actual = UserName.Companion.of("  ").getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    required = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }

            @Test
            fun whenOver100Length() {
                val actual = UserName.Companion.of("あ".repeat(UserName.Companion.MAX_LENGTH + 1)).getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    maxLength = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }
        }
    }

    private fun createErrorAssertion(actual: DomainItemError): DomainErrorAssertion =
        DomainErrorAssertion(
            name = requireNotNull(UserName::class.simpleName),
            actual = actual
        )
}
