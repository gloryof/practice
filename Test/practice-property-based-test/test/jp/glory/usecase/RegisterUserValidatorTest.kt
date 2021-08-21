package jp.glory.usecase

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getError
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Gen
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import jp.glory.domain.BirthDayGenerator
import java.time.format.DateTimeFormatter

class RegisterUserValidatorTest : DescribeSpec({

    fun assertError(
        actual: Result<Unit, ValidationErrors>,
        type: ValidationErrorType,
        field: String,
        attributes: Map<String, String>
    ) {
        val actualError = actual.getError()
        actualError.shouldNotBeNull()

        val errors = actualError.getErrors()
        errors.size shouldBe 1

        val error = errors[0]
        error.field shouldBe field

        val details = error.getDetails()
        details.size shouldBe 1

        val detail = details[0]
        detail.type shouldBe type
        detail.attributes shouldBe attributes
    }

    fun assertRequiredError(
        actual: Result<Unit, ValidationErrors>,
        field: String,
    ) = assertError(
        actual = actual,
        type = ValidationErrorType.Required,
        field = field,
        attributes = emptyMap()
    )

    fun assertInvalidValue(
        actual: Result<Unit, ValidationErrors>,
        field: String,
    ) = assertError(
        actual = actual,
        type = ValidationErrorType.InvalidValue,
        field = field,
        attributes = emptyMap()
    )

    fun assertInvalidFormat(
        actual: Result<Unit, ValidationErrors>,
        field: String,
    ) = assertError(
        actual = actual,
        type = ValidationErrorType.InvalidFormat,
        field = field,
        attributes = mapOf(
            "format" to "yyyy-MM-dd"
        )
    )

    fun createInput(
        familyName: String = "Yamada",
        givenName: String = "Taro",
        birthDay: String = "1986-12-16"
    ): RegisterUserUseCase.Input = RegisterUserUseCase.Input(
        familyName = familyName,
        givenName = givenName,
        birthDay = birthDay
    )

    describe("Sample Based Test") {
        describe("validate") {
            describe("Success") {
                it("Input is valid") {
                    val input = createInput()
                    val actual = RegisterUserValidator.validate(input)
                    actual shouldBe Ok(Unit)
                }
            }
            describe("Fail") {
                describe("Family name") {
                    val field = Fields.familyName
                    it("empty") {
                        val input = createInput(
                            familyName = ""
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertRequiredError(actual, field)
                    }
                    it("Hiragana") {
                        val input = createInput(
                            familyName = "ああああ"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidValue(actual, field)
                    }
                }
                describe("Given name") {
                    val field = Fields.givenName
                    it("empty") {
                        val input = createInput(
                            givenName = ""
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertRequiredError(actual, field)
                    }
                    it("Hiragana") {
                        val input = createInput(
                            givenName = "ああああ"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidValue(actual, field)
                    }
                }
                describe("BirthDay") {
                    val field = Fields.birthDay
                    it("empty") {
                        val input = createInput(
                            birthDay = ""
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertRequiredError(actual, field)
                    }
                    it("invalid format(year length)") {
                        val input = createInput(
                            birthDay = "986-01-01"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid format(month length)") {
                        val input = createInput(
                            birthDay = "1986-1-01"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid format(day length)") {
                        val input = createInput(
                            birthDay = "1986-01-1"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid format(lack month hyphen)") {
                        val input = createInput(
                            birthDay = "198601-01"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid format(lack day hyphen)") {
                        val input = createInput(
                            birthDay = "1986-0101"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid format(lack hyphen)") {
                        val input = createInput(
                            birthDay = "19860101"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid format(not date)") {
                        val input = createInput(
                            birthDay = "aaaa-bb-cc"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidFormat(actual, field)
                    }
                    it("invalid value(not exist date)") {
                        val input = createInput(
                            birthDay = "2100-02-29"
                        )
                        val actual = RegisterUserValidator.validate(input)
                        assertInvalidValue(actual, field)
                    }
                }
            }
        }
    }

    describe("Property Based Test") {
        describe("validate") {
            describe("Success") {
                val arbInput = UserInputGen.generate()
                checkAll(arbInput) { input ->
                    val actual = RegisterUserValidator.validate(input)
                    actual shouldBe Ok(Unit)
                }
            }
            describe("Fail") {
                describe("Family name") {
                    val field = Fields.familyName
                    it("Hiragana") {
                        val arbInput = UserInputGen.generate(
                            familyName = Arb.string(
                                codepoints = Arb.hiragana()
                            )
                                .filterNot { it.isEmpty() }
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidValue(actual, field)
                        }
                    }
                }
                describe("Given name") {
                    val field = Fields.givenName
                    it("Hiragana") {
                        val arbInput = UserInputGen.generate(
                            givenName = Arb.string(
                                codepoints = Arb.hiragana()
                            )
                                .filterNot { it.isEmpty() }
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidValue(actual, field)
                        }
                    }
                }
                describe("BirthDay") {
                    val field = Fields.birthDay
                    it("Invalid value") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateInvalidValue()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid format(year length)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateLackYearLength()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid format(month length)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateLackMonthLength()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid format(day length)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateLackDayLength()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid format(lack month hyphen)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateLackMonthHyphen()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid format(lack day hyphen)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateLackDayHyphen()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid format(lack hyphen)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateLackHyphen()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidFormat(actual, field)
                        }
                    }
                    it("invalid value(not exist date)") {
                        val arbInput = UserInputGen.generate(
                            birthDay = BirthDayGen.generateNotExistDate()
                        )
                        checkAll(arbInput) { input ->
                            val actual = RegisterUserValidator.validate(input)
                            assertInvalidValue(actual, field)
                        }
                    }
                }
            }
        }
    }
}) {
    private object Fields {
        const val familyName = "familyName"
        const val givenName = "givenName"
        const val birthDay = "birthDay"
    }
    private object UserInputGen {
        fun generate(
            familyName: Gen<String> = Arb.string(
                codepoints = Arb.alphanumeric()
            )
                .filterNot { it.isEmpty() },
            givenName: Gen<String> = Arb.string(
                codepoints = Arb.alphanumeric()
            )
                .filterNot { it.isEmpty() },
            birthDay: Gen<String> = BirthDayGen.generate()
        ): Arb<RegisterUserUseCase.Input> = Arb.bind(
            familyName,
            givenName,
            birthDay
        ) {
            familyName,
            givenName,
            birthDay ->
            RegisterUserUseCase.Input(
                familyName = familyName,
                givenName = givenName,
                birthDay = birthDay
            )
        }
    }

    private object BirthDayGen {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        private val monthLengthFormatter = DateTimeFormatter.ofPattern("yyyy-M-dd")
        private val dayLengthFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d")
        private val lackMonthHyphenFormatter = DateTimeFormatter.ofPattern("yyyyMM-dd")
        private val lackDayHyphenFormatter = DateTimeFormatter.ofPattern("yyyyMM-dd")
        private val lackHyphenFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        fun generate(): Arb<String> =
            BirthDayGenerator
                .generate()
                .map { it.format(formatter) }

        fun generateInvalidValue(): Arb<String> = Arb.string().filterNot { it.isEmpty() }

        fun generateLackYearLength(): Arb<String> =
            generate()
                .map { it.substring(1, it.length) }
        fun generateLackMonthLength(): Arb<String> =
            BirthDayGenerator
                .generate()
                .filter { it.month.value < 10 }
                .map { it.format(monthLengthFormatter) }
        fun generateLackDayLength(): Arb<String> =
            BirthDayGenerator
                .generate()
                .filter { it.dayOfMonth < 10 }
                .map { it.format(dayLengthFormatter) }

        fun generateLackMonthHyphen(): Arb<String> =
            BirthDayGenerator
                .generate()
                .map { it.format(lackMonthHyphenFormatter) }
        fun generateLackDayHyphen(): Arb<String> =
            BirthDayGenerator
                .generate()
                .map { it.format(lackDayHyphenFormatter) }
        fun generateLackHyphen(): Arb<String> =
            BirthDayGenerator
                .generate()
                .map { it.format(lackHyphenFormatter) }

        fun generateNotExistDate(): Arb<String> =
            BirthDayGenerator
                .generate()
                .map {
                    val yearMonth = it.format(
                        DateTimeFormatter.ofPattern("yyyy-MM")
                    )
                    val day = it.lengthOfMonth() + 1
                    "$yearMonth-$day"
                }
    }
}