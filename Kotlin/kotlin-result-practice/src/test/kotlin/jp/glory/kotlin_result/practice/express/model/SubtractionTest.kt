package jp.glory.kotlin_result.practice.express.model

import com.github.michaelbull.result.Ok
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubtractionTest {

    @Test
    fun pattern1() {
        val sut =  Subtraction(5, 2)
        assertEquals(Ok(3), sut.execute())
    }

    @Test
    fun pattern2() {
        val sut =  Subtraction(3, 4)
        assertEquals(Ok(-1), sut.execute())
    }

    @Test
    fun pattern3() {
        val sut =  Subtraction(0, 0)
        assertEquals(Ok(0), sut.execute())
    }

    @Test
    fun pattern4() {
        val sut =  Subtraction(3, -4)
        assertEquals(Ok(7), sut.execute())
    }

    @Test
    fun pattern5() {
        val sut =  Subtraction(-3, -4)
        assertEquals(Ok(1), sut.execute())
    }
}