package jp.glory.practice.junit.model.t08repeated

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepetitionInfo

class TestRepeated {
    @RepeatedTest(10)
    fun test(
        repetitionInfo: RepetitionInfo
    ) {
        println(repetitionInfo.currentRepetition)
        assertTrue(true)
        assertEquals(repetitionInfo.currentRepetition, repetitionInfo.currentRepetition)
    }

}