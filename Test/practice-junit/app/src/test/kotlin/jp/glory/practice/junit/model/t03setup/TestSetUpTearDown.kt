package jp.glory.practice.junit.model.t03setup

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TestSetUpTearDown {
    // When using @BeforeAll/@BeforeAfter in kotlin, need to add @JvmStatic
    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            println("Before all")
        }

        @BeforeAll
        @JvmStatic
        fun beforeAll2() {
            println("Before all")
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            println("After all")
        }

        @AfterAll
        @JvmStatic
        fun afterAll2() {
            println("After all2")
        }
    }

    @Nested
    inner class TestPlus {
        @BeforeEach
        fun beforeEach() {
            println("Before each(Test Plus)")
        }

        @Test
        fun test() {
            Assertions.assertEquals(3, Calculator.plus(1, 2))
        }
        @AfterEach
        fun afterEach() {
            println("After each(Test Plus)")
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
    @BeforeEach
    fun beforeEach() {
        println("Before each")
    }

    @BeforeEach
    fun beforeEach2() {
        println("Before each2")
    }

    @AfterEach
    fun afterEach() {
        println("After each")
    }

    @AfterEach
    fun afterEach2() {
        println("After each2")
    }

}