package jp.glory.practice.junit.model.t01basic

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestBasic {
    @Test
    fun testPlus() {
        Assertions.assertEquals(3, Calculator.plus(1, 2))
    }
    @Test
    fun testMinus() {
        Assertions.assertEquals(3, Calculator.minus(8, 5))
    }
}