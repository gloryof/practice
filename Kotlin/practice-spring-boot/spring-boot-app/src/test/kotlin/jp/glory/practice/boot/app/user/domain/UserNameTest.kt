package jp.glory.practice.boot.app.user.domain

import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOrThrow
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.user.domain.command.UserName
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
                val actual = UserName.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }

            @Test
            fun maxLength() {
                val expected = "あ".repeat(UserName.MAX_LENGTH)
                val actual = UserName.of(expected).getOrThrow { fail("Fail") }

                assertEquals(expected, actual.value)
            }
        }

        @Nested
        inner class Fail {
            @Test
            fun whenEmpty() {
                val actual = UserName.of("").getError()

                assertNotNull(actual)
                assertName(actual)
            }

            @Test
            fun whenSpaceOnly() {
                val actual = UserName.of("  ").getError()

                assertNotNull(actual)
                assertName(actual)
            }

            @Test
            fun whenOver100Length() {
                val actual = UserName.of("あ".repeat(UserName.MAX_LENGTH + 1)).getError()

                assertNotNull(actual)
                assertName(actual)
            }
        }
    }

    private fun assertName(errors: DomainErrors) {
        assertEquals(UserName::class.simpleName, errors.name)
    }
}
