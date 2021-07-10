package jp.glory.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class UserTest : DescribeSpec({
    val baseDate = LocalDate.of(2021, 3, 1)
    describe("Sample Based Test") {
        val id = UserId("test-id")
        describe("Equals base date") {
            val sut = User(
                id = id,
                birthDay = baseDate
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 0
                actual.group shouldBe Age.Group.Under10
            }
        }
        describe("1year old before 1 day") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(1).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 0
                actual.group shouldBe Age.Group.Under10
            }
        }
        describe("1year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 1
                actual.group shouldBe Age.Group.Under10
            }
        }
        describe("9year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(10).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 9
                actual.group shouldBe Age.Group.Under10
            }
        }
        describe("10year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(10)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 10
                actual.group shouldBe Age.Group.Age10s
            }
        }
        describe("9year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(20).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 19
                actual.group shouldBe Age.Group.Age10s
            }
        }
        describe("20year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(20)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 20
                actual.group shouldBe Age.Group.Age20s
            }
        }
        describe("29year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(30).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 29
                actual.group shouldBe Age.Group.Age20s
            }
        }
        describe("30year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(30)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 30
                actual.group shouldBe Age.Group.Age30s
            }
        }
        describe("39year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(40).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 39
                actual.group shouldBe Age.Group.Age30s
            }
        }
        describe("40year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(40)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 40
                actual.group shouldBe Age.Group.Age40s
            }
        }
        describe("49year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(50).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 49
                actual.group shouldBe Age.Group.Age40s
            }
        }
        describe("50year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(50)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 50
                actual.group shouldBe Age.Group.Age50s
            }
        }
        describe("59year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(60).plusDays(1)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 59
                actual.group shouldBe Age.Group.Age50s
            }
        }
        describe("60year old") {
            val sut = User(
                id = id,
                birthDay = baseDate.minusYears(60)
            )

            it("calculateAge") {
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 60
                actual.group shouldBe Age.Group.Over60
            }
        }
    }
    describe("Property Based Test") {
        it("Under10") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateUnder10BirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Under10
            }
        }
        it("Age10s") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateAge10sBirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Age10s
            }
        }
        it("Age20s") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateAge20sBirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Age20s
            }
        }
        it("Age30s") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateAge30sBirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Age30s
            }
        }
        it("Age40s") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateAge40sBirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Age40s
            }
        }
        it("Age50s") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateAge50sBirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Age50s
            }
        }
        it("Over60s") {
            val arbUser = UserGenerator.generate(
                birthDay = UserGenerator.generateOver60sBirthDay(baseDate)
            )
            checkAll(arbUser) { user ->
                val age = user.calculateAge(baseDate)
                age.value shouldBe ChronoUnit.YEARS.between(user.birthDay, baseDate).toInt()
                age.group shouldBe Age.Group.Over60
            }
        }
    }
})