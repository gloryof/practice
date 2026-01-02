package jp.glory.practice.boot.app.test.tool

import jp.glory.practice.boot.app.base.command.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.command.usecase.exception.UsecaseSpecErrorType
import kotlin.reflect.KClass
import kotlin.test.assertTrue

class UsecaseErrorsAssertion(
    private val specErrors: Set<UsecaseSpecErrorType> = emptySet(),
    private val names: Set<KClass<*>> = emptySet(),
    private val usecaseErrors: UsecaseErrors
) {
    fun assertAll() {
        if (specErrors.isNotEmpty()) {
            assertTrue(usecaseErrors.specErrors.containsAll(specErrors))
        }

        if (names.isNotEmpty()) {
            assertNames()
        }
    }

    fun assertAnyItemError() {
        assertTrue(usecaseErrors.itemErrors.isNotEmpty())
    }


    private fun assertNames() {
        val actualNames = usecaseErrors.itemErrors.map { it.name }
        val expectedNames = names.mapNotNull { it.simpleName }
        assertTrue(actualNames.containsAll(expectedNames))
    }
}
