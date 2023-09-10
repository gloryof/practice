package jp.glory.practice.junit.model.t09parameterized

import jp.glory.practice.junit.model.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

class TestParameterized {

    companion object {
        @JvmStatic
        fun testPatternMethod(): Stream<TestPattern> =
            Stream.of(*TestPattern.values())
    }

    @ParameterizedTest(name = "{0} + {0} =  ({0} * 2)")
    @ValueSource(ints =  [1, 2, 3])
    fun testValueSource(value: Int) {
        val expected = value * 2

        Assertions.assertEquals(expected, Calculator.plus(value, value))
    }

    @ParameterizedTest
    @EnumSource(TestPattern::class)
    fun testEnumSource(test: TestPattern) {
        Assertions.assertEquals(test.result, Calculator.plus(test.value1, test.value2))
    }

    @ParameterizedTest
    @MethodSource("testPatternMethod")
    fun testMethodSource(test: TestPattern) {
        Assertions.assertEquals(test.result, Calculator.plus(test.value1, test.value2))
    }

    @ParameterizedTest(name = "{0} + {1} =  {2}")
    @CsvSource(
        "1,2,3",
        "2,3,5",
        "3,4,7",
    )
    fun testCsvSource(
        value1: Int,
        value2: Int,
        expected: Int
    ) {
        Assertions.assertEquals(expected, Calculator.plus(value1, value2))
    }

    @ParameterizedTest(name = "{0} + {1} =  {2}")
    @ArgumentsSource(TestPatternArgumentProvider::class)
    fun testArgumentsSource(
        value1: Int,
        value2: Int,
        expected: Int
    ) {
        Assertions.assertEquals(expected, Calculator.plus(value1, value2))
    }

    enum class TestPattern(
        val value1: Int,
        val value2: Int,
        val result: Int
    ) {
        Pattern1(1, 2,  3),
        Pattern2(2, 3,  5),
        Pattern3(3, 4,  7)
    }

    class TestPatternArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> =
            TestPattern
                .values()
                .map { Arguments.arguments(it.value1, it.value2, it.result) }
                .let { it.stream() }
    }
}