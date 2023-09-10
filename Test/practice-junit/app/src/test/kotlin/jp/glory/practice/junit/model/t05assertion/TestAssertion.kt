package jp.glory.practice.junit.model.t05assertion

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertLinesMatch
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertNotSame
import kotlin.test.assertNull

class TestAssertion {

    @Test
    fun testAssertAll() {
        assertAll(
            { assertEquals(5, Calculator.plus(2, 3)) },
            { assertEquals(10, Calculator.plus(3, 7)) },
            { assertEquals(15, Calculator.plus(4, 11)) },
        )
    }

    @Test
    fun testAssertArraysEquals() {
        assertArrayEquals(
            arrayOf(5, 10, 15),
            arrayOf(
                Calculator.plus(2, 3),
                Calculator.plus(3, 7),
                Calculator.plus(4, 11)
            )
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
    fun testAssertFalse() {
        assertFalse(6 == Calculator.plus(2, 3))
    }

    @Test
    fun testAssertInstanceOf() {
        assertInstanceOf(Integer::class.java, Calculator.plus(2, 3))
    }

    @Test
    fun testAssertIterableEquals() {
        assertIterableEquals(
            listOf(5, 10, 15),
            listOf(
                Calculator.plus(2, 3),
                Calculator.plus(3, 7),
                Calculator.plus(4, 11)
            )
        )
    }

    @Test
    fun testAssertLinesMatch() {
        val line1 = "this is line 1"
        val line2 = "this is line 2"
        val line3 = "this is line 3"
        val line4 = "this is line 4"
        val line5 = "this is line 5"
        val lines = listOf(line1, line2, line3, line4, line5)
        assertLinesMatch(lines, lines)
    }

    @Test
    fun testAssertNotEquals() {
        assertNotEquals(6, Calculator.plus(2, 3))
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
        val actual = assertThrows(
            IllegalArgumentException::class.java,
        ) { throw IllegalArgumentException(message) }

        assertEquals(message, actual.message)
    }

    @Test
    fun testAssertTrue() {
        assertTrue(5 == Calculator.plus(2, 3))
    }
}