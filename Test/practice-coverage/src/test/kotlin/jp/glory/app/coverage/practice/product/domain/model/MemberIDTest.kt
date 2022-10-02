package jp.glory.app.coverage.practice.product.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MemberIDTest {
    @Test
    @DisplayName("Success when value is not empty")
    fun success() {
        MemberID("test")
    }

    @Test
    @DisplayName("Fail when value is  empty")
    fun fail() {
        assertThrows<IllegalArgumentException> {
            MemberID("")
        }
    }
}