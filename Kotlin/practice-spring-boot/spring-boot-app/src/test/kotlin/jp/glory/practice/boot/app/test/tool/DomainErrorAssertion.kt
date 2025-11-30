package jp.glory.practice.boot.app.test.tool

import jp.glory.practice.boot.app.base.domain.exception.DomainError
import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import kotlin.test.assertEquals

class DomainErrorAssertion(
    private val name: String,
    private val actual: DomainErrors
) {
    private val errors: MutableList<DomainError> = mutableListOf()

    fun assertName() {
        assertEquals(name, actual.name)
    }

    fun assertRequired() {
        actual.errors.contains(DomainError.REQUIRED)
    }

    fun assertMaxLength() {
        actual.errors.contains(DomainError.MAX_LENGTH)
    }

    fun assertMinLength() {
        actual.errors.contains(DomainError.MIN_LENGTH)
    }

    fun assertFormat() {
        actual.errors.contains(DomainError.FORMAT)
    }
}
