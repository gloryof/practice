package jp.glory.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class UserTest : DescribeSpec({
    fun createSut(
        id: UserId = UserId("test-user-id"),
        name: Name = Name(
            familyName = "family",
            givenName = "given"
        ),
        birthDay: LocalDate = LocalDate.now()
    ): User = User(
        id = id,
        name = name,
        birthDay = birthDay
    )

    val baseDate = LocalDate.of(2021, 3, 1)
    describe("Sample Based Test") {
        describe("calculateAge") {
            it("Equals base date") {
                val sut = createSut(
                    birthDay = baseDate
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 0
                actual.group shouldBe Age.Group.Under10
            }
            it("1year old before 1 day") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(1).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 0
                actual.group shouldBe Age.Group.Under10
            }
            it("1year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 1
                actual.group shouldBe Age.Group.Under10
            }
            it("9year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(10).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 9
                actual.group shouldBe Age.Group.Under10
            }
            it("10year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(10)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 10
                actual.group shouldBe Age.Group.Age10s
            }
            it("9year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(20).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 19
                actual.group shouldBe Age.Group.Age10s
            }
            it("20year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(20)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 20
                actual.group shouldBe Age.Group.Age20s
            }
            it("29year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(30).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 29
                actual.group shouldBe Age.Group.Age20s
            }
            it("30year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(30)
                )

                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 30
                actual.group shouldBe Age.Group.Age30s
            }
            it("39year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(40).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 39
                actual.group shouldBe Age.Group.Age30s
            }
            it("40year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(40)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 40
                actual.group shouldBe Age.Group.Age40s
            }
            it("49year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(50).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 49
                actual.group shouldBe Age.Group.Age40s
            }
            it("50year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(50)
                )

                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 50
                actual.group shouldBe Age.Group.Age50s
            }
            it("59year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(60).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 59
                actual.group shouldBe Age.Group.Age50s
            }
            it("60year old") {
                val sut = createSut(
                    birthDay = baseDate.minusYears(60)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 60
                actual.group shouldBe Age.Group.Over60
            }
        }
    }
    describe("Property Based Test") {
        describe("calculateAge") {
            it("Under10") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateUnder10BirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Under10
                }
            }
            it("Age10s") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateAge10sBirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Age10s
                }
            }
            it("Age20s") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateAge20sBirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Age20s
                }
            }
            it("Age30s") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateAge30sBirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Age30s
                }
            }
            it("Age40s") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateAge40sBirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Age40s
                }
            }
            it("Age50s") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateAge50sBirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Age50s
                }
            }
            it("Over60s") {
                val arbUser = UserGenerator.generate(
                    birthDay = BirthDayGenerator.generateOver60sBirthDay(baseDate)
                )
                checkAll(arbUser) { user ->
                    val age = user.calculateAge(baseDate)
                    age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                    age.group shouldBe Age.Group.Over60
                }
            }
        }
    }
})

