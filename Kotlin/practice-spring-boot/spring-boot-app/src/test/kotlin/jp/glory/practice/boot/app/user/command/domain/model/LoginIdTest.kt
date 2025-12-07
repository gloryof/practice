package jp.glory.practice.boot.app.user.command.domain.model

import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOrThrow
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
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

class LoginIdTest {
    @Nested
    inner class Of {
        @Nested
        inner class Success {
            @Test
            fun normal() {
                val expected = "test"
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun maxLength() {
                val expected = "a".repeat(LoginId.Companion.MAX_LENGTH)
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun alphabet() {
                val expected = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun numeric() {
                val expected = "1234567890"
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun hyphen() {
                val expected = "-"
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun dot() {
                val expected = "."
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun underbar() {
                val expected = "_"
                val actual = LoginId.Companion.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun whenEmpty() {
                val actual = LoginId.Companion.of("").getError()

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertRequired()
            }

            @Test
            fun whenSpaceOnly() {
                val actual = LoginId.Companion.of("  ").getError()

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertRequired()
            }

            @Test
            fun whenOver100Length() {
                val actual = LoginId.Companion.of("あ".repeat(LoginId.Companion.MAX_LENGTH + 1)).getError()

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertMaxLength()
            }

            @ParameterizedTest
            @ArgumentsSource(InvalidCharacterProvider::class)
            fun invalidCharacters(value: String) {
                val actual = LoginId.Companion.of(value).getError()

                assertNotNull(actual)
                val assertion = createErrorAssertion(actual)
                assertion.assertFormat()
            }
        }
    }

    private fun createErrorAssertion(actual: DomainErrors): DomainErrorAssertion =
        DomainErrorAssertion(
            name = requireNotNull(LoginId::class.simpleName),
            actual = actual
        )

    private fun assertName(errors: DomainErrors) {
        assertEquals(LoginId::class.simpleName, errors.name)
    }

    class InvalidCharacterProvider : ArgumentsProvider {
        private val symbols: List<String> = listOf(
            "Ａ", "１", "「", " ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "=",
            "^", "~", "¥", "|", "@", "`", "[", "{", ";", "+", ":", "*", "]", "}", ",",
            "<", ">", "/", "?", "\\"
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
