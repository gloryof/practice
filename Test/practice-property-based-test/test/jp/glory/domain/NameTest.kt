package jp.glory.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class NameTest : DescribeSpec({
    describe("Sample Based Test") {
        describe("Get japan style") {
            it("Family name is first") {
                val sut = Name(
                    familyName = "family",
                    givenName = "given"
                )

                sut.getJapanStyle() shouldBe "${sut.familyName} ${sut.givenName}"
            }
        }
        describe("Get foreign style") {
            it("Given name is first") {
                val sut = Name(
                    familyName = "family",
                    givenName = "given"
                )

                sut.getForeignStyle() shouldBe "${sut.givenName} ${sut.familyName}"
            }
        }
    }
    describe("Property Based Test") {
        describe("Get japan style") {
            it("Family name is first") {
                val arb = Arb.bind(
                    Arb.string(),
                    Arb.string(),
                ) {
                    familyName,
                    givenName ->
                    Name(
                        familyName = familyName,
                        givenName = givenName
                    )
                }

                checkAll(arb) { sut ->
                    sut.getJapanStyle() shouldBe "${sut.familyName} ${sut.givenName}"
                }
            }
        }
        describe("Get foreign style") {
            it("Given name is first") {
                val arb = Arb.bind(
                    Arb.string(),
                    Arb.string(),
                ) {
                        familyName,
                        givenName ->
                    Name(
                        familyName = familyName,
                        givenName = givenName
                    )
                }

                checkAll(arb) { sut ->
                    sut.getForeignStyle() shouldBe "${sut.givenName} ${sut.familyName}"
                }
            }
        }
    }
})