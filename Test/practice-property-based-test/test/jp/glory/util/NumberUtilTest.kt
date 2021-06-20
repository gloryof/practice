package jp.glory.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.forAll
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

class NumberUtilTest : DescribeSpec({
    describe("isNumeric") {
        context("Sample based test") {
            it("Numeric") {
                NumberUtil.isNumeric("1") shouldBe true
                NumberUtil.isNumeric("-1") shouldBe true
                NumberUtil.isNumeric("01") shouldBe true
                NumberUtil.isNumeric("123456789") shouldBe true
            }

            it("Not numeric(Single byte pattern)") {
                NumberUtil.isNumeric("a") shouldBe false
                NumberUtil.isNumeric("i") shouldBe false
                NumberUtil.isNumeric("!") shouldBe false
                NumberUtil.isNumeric(" ") shouldBe false
                NumberUtil.isNumeric("\t") shouldBe false
            }

            it("Not numeric(Multi byte pattern)") {
                NumberUtil.isNumeric("Ａ") shouldBe false
                NumberUtil.isNumeric("ｉ") shouldBe false
                NumberUtil.isNumeric("！") shouldBe false
                NumberUtil.isNumeric("　") shouldBe false
                NumberUtil.isNumeric("１") shouldBe false
            }
        }

        context("Property based test") {
            it("Numeric") {
                forAll<Int> { a ->
                    NumberUtil.isNumeric(a.toString())
                }
            }

            it("Not numeric(Single byte pattern)") {
                val pattern = Arb.string().filter { !it.matches(Regex("[-]*[0-9]+")) }
                forAll(pattern) { a ->
                    !NumberUtil.isNumeric(a)
                }
            }

            it("Not numeric(Katakana pattern)") {
                val pattern = Arb.string(
                    codepoints = Arb.katakana()
                )
                forAll(pattern) { a ->
                    !NumberUtil.isNumeric(a)
                }
            }

            it("Not numeric(Hiragana pattern)") {
                val pattern = Arb.string(
                    codepoints = Arb.hiragana()
                )
                forAll(pattern) { a ->
                    !NumberUtil.isNumeric(a)
                }
            }
        }
    }
})