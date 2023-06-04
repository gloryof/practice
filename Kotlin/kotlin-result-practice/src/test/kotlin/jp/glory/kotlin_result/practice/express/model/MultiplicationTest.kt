package jp.glory.kotlin_result.practice.express.model

import com.github.michaelbull.result.Ok
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MultiplicationTest {

    @Test
    fun pattern1() {
        val sut =  Multiplication(3, 2)
        assertEquals(Ok(6), sut.execute())
    }

    @Test
    fun pattern2() {
        val sut =  Multiplication(0, 4)
        assertEquals(Ok(0), sut.execute())
    }

    @Test
    fun pattern3() {
        val sut =  Multiplication(4, 0)
        assertEquals(Ok(0), sut.execute())
    }

    @Test
    fun pattern4() {
        val sut =  Multiplication(2, -3)
        assertEquals(Ok(-6), sut.execute())
    }

    @Test
    fun pattern5() {
        val sut =  Multiplication(-2, 3)
        assertEquals(Ok(-6), sut.execute())
    }
}