package jp.glory.practice.jazzer.domain

import com.code_intelligence.jazzer.api.FuzzedDataProvider
import com.code_intelligence.jazzer.junit.FuzzTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class NameTest {

    @Nested
    inner class NormalTest {
        @Nested
        inner class TestGetJapanStyle {
            @DisplayName("Get japan style")
            @Test
            fun test() {
                val sut = Name(
                    familyName = "family",
                    givenName = "given"
                )

                val expected = "${sut.familyName} ${sut.givenName}"
                assertEquals(expected, sut.getJapanStyle())
            }
        }

        @Nested
        inner class TestGetForeignStyle {
            @DisplayName("Get foreign style")
            @Test
            fun test() {
                val sut = Name(
                    familyName = "family",
                    givenName = "given"
                )

                val expected = "${sut.givenName} ${sut.familyName}"
                assertEquals(expected, sut.getForeignStyle())
            }
        }
        @Nested
        inner class Fuzzing {
            @FuzzTest(maxDuration = "10s")
            fun test(data: FuzzedDataProvider) {
                val sut = Name(
                    familyName = data.consumeRemainingAsString(),
                    givenName = data.consumeRemainingAsString()
                )

                val japanStyle = "${sut.familyName} ${sut.givenName}"
                val foreignStyle = "${sut.givenName} ${sut.familyName}"
                assertEquals(japanStyle, sut.getJapanStyle())
                assertEquals(foreignStyle, sut.getForeignStyle())
            }
        }
    }
}