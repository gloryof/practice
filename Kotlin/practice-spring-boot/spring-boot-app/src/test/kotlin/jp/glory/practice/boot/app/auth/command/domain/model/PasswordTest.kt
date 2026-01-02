package jp.glory.practice.boot.app.auth.command.domain.model

import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOrThrow
import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.test.tool.DomainErrorAssertion
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.fail

class PasswordTest {
    @Nested
    inner class Of {
        @Nested
        inner class Success {
            @Test
            fun normal() {
                val expected = "test-test-test-test-test-test-test"
                val actual = Password.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun maxLength() {
                val expected = "a".repeat(Password.Companion.MAX_LENGTH)
                val actual = Password.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun minLength() {
                val expected = "a".repeat(Password.Companion.MIN_LENGTH)
                val actual = Password.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun alphabet() {
                val expected = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                val actual = Password.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun numeric() {
                val expected = "1234567890"
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @ParameterizedTest
            @ArgumentsSource(ValidCharacterProvider::class)
            fun symbol(value: String) {
                val expected = "valid-string-min¥$value"
                val actual = Password.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun whenEmpty() {
                val actual = Password.Companion.of("").getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    required = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }

            @Test
            fun whenSpaceOnly() {
                val actual = Password.Companion.of(" ".repeat(Password.Companion.MIN_LENGTH)).getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    required = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }

            @Test
            fun whenOver100Length() {
                val actual = Password.Companion.of("あ".repeat(Password.Companion.MAX_LENGTH + 1)).getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    maxLength = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }


            @Test
            fun whenLess16Length() {
                val actual = Password.Companion.of("あ".repeat(Password.Companion.MIN_LENGTH - 1)).getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    minLength = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }

            @ParameterizedTest
            @ArgumentsSource(InvalidCharacterProvider::class)
            fun invalidCharacters(value: String) {
                val actual = Password.Companion.of(value).getError()
                val assertionConfig = DomainErrorAssertion.AssertionConfig(
                    format = true
                )

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertion(assertionConfig)
            }
        }
    }

    private fun createErrorAssertion(actual: DomainItemError): DomainErrorAssertion =
        DomainErrorAssertion(
            name = requireNotNull(Password::class.simpleName),
            actual = actual
        )

    class ValidCharacterProvider : ArgumentsProvider {
        private val symbols: List<String> = listOf(
            "!", "\"", "#", "$", "%", "&", "'", "(", ")", "=", "^", "~", "¥", "|", "@",
            "`", "[", "{", ";", "+", ":", "*", "]", "}", ",", "<", ">", "/", "?", "\\"
        )

        override fun provideArguments(
            parameters: ParameterDeclarations,
            context: ExtensionContext
        ): Stream<out Arguments> {
            val argumentsList = symbols.map {
                Arguments.of(it)
            }

            return argumentsList.stream()
        }
    }

    class InvalidCharacterProvider : ArgumentsProvider {
        private val symbols: List<String> = listOf(
            "Ａ", "１", "「", " "
        )

        override fun provideArguments(
            parameters: ParameterDeclarations,
            context: ExtensionContext
        ): Stream<out Arguments> {
            val argumentsList = symbols.map {
                Arguments.of(it)
            }

            return argumentsList.stream()
        }
    }
}
