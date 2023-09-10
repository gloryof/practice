package jp.glory.practice.junit.model.t02nested

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TestNested {
    @Nested
    inner class TestPlus {
        @Test
        fun test() {
            Assertions.assertEquals(3, Calculator.plus(1, 2))
        }
    }
    @Nested
    inner class TestMinus {
        @Nested
        inner class WhenPositiveValue {
            @Test
            fun test() {
                Assertions.assertEquals(3, Calculator.minus(8, 5))
            }
        }
        @Nested
        inner class WhenNegativeValue {
            @Test
            fun test() {
                Assertions.assertEquals(-3, Calculator.minus(5, 8))
            }
        }

        @Test
        fun testZero() {
            Assertions.assertEquals(0, Calculator.minus(0, 0))
        }
    }
}