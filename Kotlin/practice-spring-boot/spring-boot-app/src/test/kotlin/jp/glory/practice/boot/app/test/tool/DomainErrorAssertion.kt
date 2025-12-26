package jp.glory.practice.boot.app.test.tool

import jp.glory.practice.boot.app.base.domain.exception.DomainItemError
import jp.glory.practice.boot.app.base.domain.exception.DomainItemErrorType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DomainErrorAssertion(
    private val name: String,
    private val actual: DomainItemError,
) {
    fun assertion(config: AssertionConfig) {
        val assertions = listOf(
            Pair(true, ::assertName),
            Pair(config.required, ::assertRequired),
            Pair(config.maxLength, ::assertMaxLength),
            Pair(config.minLength, ::assertMinLength),
            Pair(config.format, ::assertFormat),
            Pair(config.dateIsAfter, ::assertDateIsAfter)
        )

        assertions
            .filter { (condition, _) -> condition }
            .map { (_, assertionFunction) -> assertionFunction }
            .forEach { function -> function() }
    }

    private fun assertName() {
        assertEquals(name, actual.name)
    }

    private fun assertRequired() {
        assertTrue(actual.errors.contains(DomainItemErrorType.REQUIRED))
    }

    private fun assertMaxLength() {
        assertTrue(actual.errors.contains(DomainItemErrorType.MAX_LENGTH))
    }

    private fun assertMinLength() {
        assertTrue(actual.errors.contains(DomainItemErrorType.MIN_LENGTH))
    }

    private fun assertFormat() {
        assertTrue(actual.errors.contains(DomainItemErrorType.FORMAT))
    }

    private fun assertDateIsAfter() {
        assertTrue(actual.errors.contains(DomainItemErrorType.DATE_IS_AFTER))
    }

    class AssertionConfig(
        val required: Boolean = false,
        val maxLength: Boolean = false,
        val minLength: Boolean = false,
        val format: Boolean = false,
        val dateIsAfter: Boolean = false
    )
}
