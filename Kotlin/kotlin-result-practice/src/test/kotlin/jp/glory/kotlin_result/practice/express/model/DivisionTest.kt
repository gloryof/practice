package jp.glory.kotlin_result.practice.express.model

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DivisionTest {

    @Test
    fun pattern1() {
        val sut =  Division(6, 2)
        assertEquals(Ok(3), sut.execute())
    }

    @Test
    fun pattern2() {
        val sut =  Division(5, 2)
        assertEquals(Ok(2), sut.execute())
    }

    @Test
    fun pattern3() {
        val sut =  Division(6, -2)
        assertEquals(Ok(-3), sut.execute())
    }

    @Test
    fun pattern4() {
        val sut =  Division(-6, 2)
        assertEquals(Ok(-3), sut.execute())
    }


    @Test
    fun pattern5() {
        val sut =  Division(-6, -2)
        assertEquals(Ok(3), sut.execute())
    }

    @Test
    fun pattern6() {
        val sut =  Division(6, 0)
        assertEquals(Err(ErrorInfo.DIV_BY_ZERO), sut.execute())
    }
}