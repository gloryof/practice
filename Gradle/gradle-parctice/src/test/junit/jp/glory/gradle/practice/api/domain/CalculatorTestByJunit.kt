package jp.glory.gradle.practice.api.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CalculatorTestByJunit {
    @Test
    fun success() {
        assertEquals(0, Calculator.calculate(10, 10))
    }
}