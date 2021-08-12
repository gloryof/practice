package jp.glory.usecase

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getError
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import jp.glory.domain.UserGenerator
import java.time.format.DateTimeFormatter

class RegisterUserValidatorTest : DescribeSpec({

    fun assertError(
        actual: Result<Unit, ValidationErrors>,
        type: ValidationErrorType,
        attributes: Map<String, String>
    ) {
        val actualError = actual.getError()
        actualError.shouldNotBeNull()

        val errors = actualError.getErrors()
        errors.size shouldBe 1

        val error = errors[0]
        error.field shouldBe Fields.birthDay

        val details = error.getDetails()
        details.size shouldBe 1

        val detail = details[0]
        detail.type shouldBe type
        detail.attributes shouldBe attributes
    }

    fun assertRequiredError(
        actual: Result<Unit, ValidationErrors>,
    ) = assertError(
        actual = actual,
        type = ValidationErrorType.Required,
        attributes = emptyMap()
    )

    fun assertInvalidValue(
        actual: Result<Unit, ValidationErrors>,
    ) = assertError(
        actual = actual,
        type = ValidationErrorType.InvalidValue,
        attributes = emptyMap()
    )

    fun assertInvalidFormat(
        actual: Result<Unit, ValidationErrors>,
    ) = assertError(
        actual = actual,
        type = ValidationErrorType.InvalidFormat,
        attributes = mapOf(
            "format" to "yyyy-MM-dd"
        )
    )

    describe("Sample Based Test") {
        describe("validate") {
            describe("Success") {
                it("Input is valid") {
                    val input = RegisterUserUseCase.Input(
                        birthDay = "1986-01-02"
                    )
                    val actual = RegisterUserValidator.validate(input)
                    actual shouldBe Ok(Unit)
                }
            }
            describe("Fail") {
                describe("BirthDay") {
                    it("empty") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = ""
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertRequiredError(actual)
                    }
                    it("invalid format(year length)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "986-01-01"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid format(month length)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "1986-1-01"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid format(day length)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "1986-01-1"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid format(lack month hyphen)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "198601-01"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid format(lack day hyphen)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "1986-0101"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid format(lack hyphen)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "19860101"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid format(not date)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "aaaa-bb-cc"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual)
                    }
                    it("invalid value(not exist date)") {
                        val input = RegisterUserUseCase.Input(
                            birthDay = "2100-02-29"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidValue(actual)
                    }
                }
            }
        }
    }

    describe("Property Based Test") {
        describe("validate") {
            describe("Success") {
                val arbDate = BirthDayGenerator.generate()
                checkAll(arbDate) { input ->
                    val actual = RegisterUserValidator.validate(input)
                    actual shouldBe Ok(Unit)
                }
            }
            describe("Fail") {
                describe("BirthDay") {
                    describe("Invalid value") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateInvalidValue()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid format(year length)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateLackYearLength()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid format(month length)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateLackMonthLength()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid format(day length)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateLackDayLength()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid format(lack month hyphen)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateLackMonthHyphen()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid format(lack day hyphen)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateLackDayHyphen()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid format(lack hyphen)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateLackHyphen()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual)
                        }
                    }
                    describe("invalid value(not exist date)") {
                        val arbDate = BirthDayGenerator.generate(
                            birthDay = BirthDayGenerator.generateNotExistDate()
                        )
                        checkAll(arbDate) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidValue(actual)
                        }
                    }

                }

            }
        }
    }
}) {
    private object Fields {
        const val birthDay = "birthDay"
    }
    private object BirthDayGenerator {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        private val monthLengthFormatter = DateTimeFormatter.ofPattern("yyyy-M-dd")
        private val dayLengthFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d")
        private val lackMonthHyphenFormatter = DateTimeFormatter.ofPattern("yyyyMM-dd")
        private val lackDayHyphenFormatter = DateTimeFormatter.ofPattern("yyyyMM-dd")
        private val lackHyphenFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        fun generate(
            birthDay: Arb<String> = generateValid()
        ): Arb<RegisterUserUseCase.Input> = birthDay.map { RegisterUserUseCase.Input(it) }

        fun generateValid(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .map { it.format(formatter) }

        fun generateInvalidValue(): Arb<String> = Arb.string().filterNot { it.isEmpty() }

        fun generateLackYearLength(): Arb<String> =
            generateValid()
                .map { it.substring(1, it.length) }
        fun generateLackMonthLength(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .filter { it.month.value < 10 }
                .map { it.format(monthLengthFormatter) }
        fun generateLackDayLength(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .filter { it.dayOfMonth < 10 }
                .map { it.format(dayLengthFormatter) }

        fun generateLackMonthHyphen(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .map { it.format(lackMonthHyphenFormatter) }
        fun generateLackDayHyphen(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .map { it.format(lackDayHyphenFormatter) }
        fun generateLackHyphen(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .map { it.format(lackHyphenFormatter) }

        fun generateNotExistDate(): Arb<String> =
            UserGenerator
                .generateArbBirthDay()
                .map {
                    val yearMonth = it.format(
                        DateTimeFormatter.ofPattern("yyyy-MM")
                    )
                    val day = it.lengthOfMonth() + 1
                    "$yearMonth-$day"
                }
    }
}