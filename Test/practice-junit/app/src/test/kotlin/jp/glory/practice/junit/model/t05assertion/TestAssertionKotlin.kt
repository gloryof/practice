package jp.glory.practice.junit.model.t05assertion

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertNull

class TestAssertionKotlin {

    @Test
    fun testAssertAll() {
        assertAll(
            { assertEquals(5, Calculator.plus(2, 3)) },
            { assertEquals(10, Calculator.plus(3, 7)) },
            { assertEquals(15, Calculator.plus(4, 11)) },
        )
    }

    @Test
    fun testAssertDoesNotThrow() {
        assertDoesNotThrow {
            Calculator.plus(2, 3)
        }
    }

    @Test
    fun testAssertEquals() {
        assertEquals(5, Calculator.plus(2, 3))
    }


    @Test
    @SuppressWarnings
    fun testAssertNotSame() {
        assertNotSame(Integer(5), Calculator.plus(2, 3) as Integer)
    }

    @Test
    fun testAssertNull() {
        assertNull(null)
    }

    @Test
    fun testAssertThrows() {
        val message = "test"
        val actual = assertThrows<IllegalArgumentException> {
            throw IllegalArgumentException(message)
        }

        assertEquals(message, actual.message)
    }
}