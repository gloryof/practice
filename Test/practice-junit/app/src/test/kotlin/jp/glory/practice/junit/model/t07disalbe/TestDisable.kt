package jp.glory.practice.junit.model.t07disalbe

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("This test is not execute")
class TestDisable {
    @Test
    fun test() {
        assertTrue(true)
    }
}