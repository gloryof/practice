package jp.glory.practice.junit.model.t06assumptions

import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class TestAssumptions {
    @Test
    fun ifTrue() {
        Assumptions.assumeTrue(true)
        assertTrue(true)
    }
    @Test
    fun ifFalse() {
        Assumptions.assumeTrue(false)
        assertTrue(true)
    }
}