package jp.glory.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.checkAll
import io.mockk.every
import io.mockk.mockk
import jp.glory.domain.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterUserUseCaseTest : DescribeSpec({
    fun createSut(
        repository: UserRepository = mockk()
    ): RegisterUserUseCase = RegisterUserUseCase(
        repository = repository
    )

    describe("Sample Based Test") {
        describe("Register") {
            describe("Success") {
                it("Input is valid") {
                    val id = UserId("test-id")
                    val input = RegisterUserUseCase.Input(
                        birthDay = "1986-12-16"
                    )
                    val user = User(
                        id = id,
                        birthDay = LocalDate.of(1986, 12, 16)
                    )
                    val sut = createSut(
                        repository = mockk {
                            every { generateId() } returns Ok(id)
                            every { save(user) } returns Ok(Unit)
                        }
                    )

                    val actual = sut.register(input)

                    val result = actual.get()
                    result.shouldNotBeNull()
                    result.value shouldBe id.value
                }
            }

            describe("Fail") {
                it("Invalid value") {
                    val input = RegisterUserUseCase.Input(
                        birthDay = ""
                    )
                    val sut = createSut()

                    val actual = sut.register(input)

                    val result = actual.getError()
                    result.shouldNotBeNull()
                    result.shouldBeTypeOf<ValidationErrors>()
                }
                it("When generate is fail") {
                    val input = RegisterUserUseCase.Input(
                        birthDay = "1986-12-16"
                    )
                    val sut = createSut(
                        repository = mockk {
                            every { generateId() } returns Err(DomainError.Unknown)
                        }
                    )

                    val actual = sut.register(input)

                    val result = actual.getError()
                    result.shouldNotBeNull()
                    result.shouldBeTypeOf<UnknownError>()
                }
                it("When save is fail") {
                    val id = UserId("test-id")
                    val input = RegisterUserUseCase.Input(
                        birthDay = "1986-12-16"
                    )
                    val user = User(
                        id = id,
                        birthDay = LocalDate.of(1986, 12, 16)
                    )
                    val sut = createSut(
                        repository = mockk {
                            every { generateId() } returns Ok(id)
                            every { save(user) } returns Err(DomainError.Unknown)
                        }
                    )

                    val actual = sut.register(input)

                    val result = actual.getError()
                    result.shouldNotBeNull()
                    result.shouldBeTypeOf<UnknownError>()
                }
            }
        }
    }

    describe("Property Based Test") {
        describe("Register") {

            describe("Success") {
                val arb = Arb.bind(
                    UserGenerator.generateArbBirthDay(),
                    UserGenerator.generateId()
                ) {
                    birthDay,
                    id ->
                    RegisterData(
                        birthDay = birthDay,
                        id = id
                    )
                }

                checkAll(arb) { data ->
                    val input = RegisterUserUseCase.Input(
                        birthDay = data.birthDay.format(DateTimeFormatter.ISO_DATE)
                    )
                    val user = User(
                        id = data.id,
                        birthDay = data.birthDay
                    )
                    val sut = createSut(
                        repository = mockk {
                            every { generateId() } returns Ok(data.id)
                            every { save(user) } returns Ok(Unit)
                        }
                    )

                    val actual = sut.register(input)

                    val result = actual.get()
                    result.shouldNotBeNull()
                    result.value shouldBe data.id.value
                }
            }

            describe("Fail") {
                it("Invalid value") {
                    val arb = Arb.bind(
                        UserGenerator.generateArbBirthDay(),
                        UserGenerator.generateId()
                    ) {
                        birthDay,
                        id ->
                        RegisterData(
                            birthDay = birthDay,
                            id = id
                        )
                    }

                    checkAll(arb) { data ->
                        val input = RegisterUserUseCase.Input(
                            birthDay = data.birthDay
                                .format(DateTimeFormatter.ofPattern("MM-yyyy-dd"))
                        )
                        val sut = createSut()

                        val actual = sut.register(input)

                        val result = actual.getError()
                        result.shouldNotBeNull()
                        result.shouldBeTypeOf<ValidationErrors>()
                    }
                }
                it("When generate is fail") {
                    val arb = Arb.bind(
                        UserGenerator.generateArbBirthDay(),
                        UserGenerator.generateId()
                    ) {
                            birthDay,
                            id ->
                        RegisterData(
                            birthDay = birthDay,
                            id = id
                        )
                    }

                    checkAll(arb) { data ->
                        val input = RegisterUserUseCase.Input(
                            birthDay = data.birthDay.format(DateTimeFormatter.ISO_DATE)
                        )
                        val sut = createSut(
                            repository = mockk {
                                every { generateId() } returns Err(DomainError.Unknown)
                            }
                        )

                        val actual = sut.register(input)

                        val result = actual.getError()
                        result.shouldNotBeNull()
                        result.shouldBeTypeOf<UnknownError>()
                    }
                }
                it("When save is fail") {
                    val arb = Arb.bind(
                        UserGenerator.generateArbBirthDay(),
                        UserGenerator.generateId()
                    ) {
                            birthDay,
                            id ->
                        RegisterData(
                            birthDay = birthDay,
                            id = id
                        )
                    }

                    checkAll(arb) { data ->
                        val input = RegisterUserUseCase.Input(
                            birthDay = data.birthDay.format(DateTimeFormatter.ISO_DATE)
                        )
                        val user = User(
                            id = data.id,
                            birthDay = data.birthDay
                        )
                        val sut = createSut(
                            repository = mockk {
                                every { generateId() } returns Ok(data.id)
                                every { save(user) } returns Err(DomainError.Unknown)
                            }
                        )

                        val actual = sut.register(input)

                        val result = actual.getError()
                        result.shouldNotBeNull()
                        result.shouldBeTypeOf<UnknownError>()
                    }
                }
            }
        }
    }
}) {
   class RegisterData(
       val birthDay: LocalDate,
       val id: UserId,
   )
}