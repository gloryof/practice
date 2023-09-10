package jp.glory.practice.junit.model.t04display

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator.IndicativeSentences
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores
import org.junit.jupiter.api.DisplayNameGenerator.Simple
import org.junit.jupiter.api.DisplayNameGenerator.Standard
import org.junit.jupiter.api.IndicativeSentencesGeneration
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream


@DisplayName("Test calculator")
class TestDisplayName {
    @Nested
    @DisplayName("Standard")
    @DisplayNameGeneration(Standard::class)
    inner class TestStandard {
        @ParameterizedTest(name = "({0} * 3) + ({0} * 2) =  ({0} * 5)")
        @ValueSource(ints =  [1, 2, 3])
        fun When_1_plus_2_is_3(value: Int) {
            assertionValues(value)
        }
    }

    @Nested
    @DisplayName("Simple")
    @DisplayNameGeneration(Simple::class)
    inner class TestSimple {
        @ParameterizedTest
        @ValueSource(ints =  [1, 2, 3])
        fun When_1_plus_2_is_3(value: Int) {
            assertionValues(value)
        }
    }

    @Nested
    @DisplayName("ReplaceUnderscores")
    @DisplayNameGeneration(ReplaceUnderscores::class)
    inner class TestReplaceUnderscores {
        @ParameterizedTest
        @ValueSource(ints =  [1, 2, 3])
        fun When_1_plus_2_is_3(value: Int) {
            assertionValues(value)
        }
    }

    @Nested
    @DisplayName("IndicativeSentences")
    @DisplayNameGeneration(IndicativeSentences::class)
    inner class TestIndicativeSentences {

        @ParameterizedTest
        @ValueSource(ints =  [1, 2, 3])
        fun When_1_plus_2_is_3(value: Int) {
            assertionValues(value)
        }
    }

    @Nested
    @DisplayName("IndicativeSentences2")
    @IndicativeSentencesGeneration
    inner class TestIndicativeSentences2 {

        @ParameterizedTest
        @ValueSource(ints =  [1, 2, 3])
        fun When_1_plus_2_is_3(value: Int) {
            assertionValues(value)
        }
    }

    private fun assertionValues(value: Int) {

        val value1 = value * 3
        val value2 = value * 2
        val expected = value1 + value2

        Assertions.assertEquals(expected, Calculator.plus(value1, value2))
    }
}