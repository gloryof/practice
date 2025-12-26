package jp.glory.practice.boot.app.test.tool

import jp.glory.practice.boot.app.base.domain.exception.DomainErrors
import jp.glory.practice.boot.app.base.domain.exception.DomainSpecErrorType
import kotlin.reflect.KClass
import kotlin.test.assertTrue

class DomainErrorsAssertion(
    private val specErrors: Set<DomainSpecErrorType> = emptySet(),
    private val names: Set<KClass<*>> = emptySet(),
    private val domainErrors: DomainErrors
) {
    fun assertAll() {
        if (specErrors.isNotEmpty()) {
            assertTrue(domainErrors.specErrors.containsAll(specErrors))
        }

        if (names.isNotEmpty()) {
            assertNames()
        }
    }


    private fun assertNames() {
        val actualNames = domainErrors.itemErrors.map { it.name }
        val expectedNames = names.mapNotNull { it.simpleName }
        assertTrue(actualNames.containsAll(expectedNames))
    }
}
