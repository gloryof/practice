package jp.glory.gradle.practice.api.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

class CalculatorTestByKotestPbt : DescribeSpec({
    describe("calculate") {
        it("return value") {
            checkAll<Int, Int> { a, b ->
                val expected = a - b
                println("a = $a : b = $b")
                Calculator.calculate(a, b) shouldBe expected
            }
        }
    }
})