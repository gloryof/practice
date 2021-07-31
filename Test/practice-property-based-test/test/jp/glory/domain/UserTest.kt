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
        describe("calculateAge") {
            it("Equals base date") {
                val sut = User(
                    id = id,
                    birthDay = baseDate
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 0
                actual.group shouldBe Age.Group.Under10
            }
            it("1year old before 1 day") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(1).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 0
                actual.group shouldBe Age.Group.Under10
            }
            it("1year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 1
                actual.group shouldBe Age.Group.Under10
            }
            it("9year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(10).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 9
                actual.group shouldBe Age.Group.Under10
            }
            it("10year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(10)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 10
                actual.group shouldBe Age.Group.Age10s
            }
            it("9year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(20).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 19
                actual.group shouldBe Age.Group.Age10s
            }
            it("20year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(20)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 20
                actual.group shouldBe Age.Group.Age20s
            }
            it("29year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(30).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 29
                actual.group shouldBe Age.Group.Age20s
            }
            it("30year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(30)
                )

                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 30
                actual.group shouldBe Age.Group.Age30s
            }
            it("39year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(40).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 39
                actual.group shouldBe Age.Group.Age30s
            }
            it("40year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(40)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 40
                actual.group shouldBe Age.Group.Age40s
            }
            it("49year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(50).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 49
                actual.group shouldBe Age.Group.Age40s
            }
            it("50year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(50)
                )

                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 50
                actual.group shouldBe Age.Group.Age50s
            }
            it("59year old") {
                val sut = User(
                    id = id,
                    birthDay = baseDate.minusYears(60).plusDays(1)
                )
                val actual = sut.calculateAge(baseDate)
                actual.value shouldBe 59
                actual.group shouldBe Age.Group.Age50s
            }
            it("60year old") {
                val sut = User(
                    id = id,
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
    }
})

class ZodiacSignTest : DescribeSpec({
    val ariesFrom = LocalDate.of(2021, 3, 21)
    val ariesTo = LocalDate.of(2021, 4, 19)
    val taurusFrom = LocalDate.of(2021, 4, 20)
    val taurusTo = LocalDate.of(2021, 5, 20)
    val geminiFrom = LocalDate.of(2021, 5, 21)
    val geminiTo = LocalDate.of(2021, 6, 21)
    val cancerFrom = LocalDate.of(2021, 6, 22)
    val cancerTo = LocalDate.of(2021, 7, 22)
    val leoFrom = LocalDate.of(2021, 7, 23)
    val leoTo = LocalDate.of(2021, 8, 22)
    val virgoFrom = LocalDate.of(2021, 8, 23)
    val virgoTo = LocalDate.of(2021, 9, 22)
    val libraFrom = LocalDate.of(2021, 9, 23)
    val libraTo = LocalDate.of(2021, 10, 23)
    val scorpiusFrom = LocalDate.of(2021, 10, 24)
    val scorpiusTo = LocalDate.of(2021, 11, 22)
    val sagittariusFrom = LocalDate.of(2021, 11, 23)
    val sagittariusTo = LocalDate.of(2021, 12,21)
    val capricornFrom = LocalDate.of(2021, 12, 22)
    val capricornTo = LocalDate.of(2022, 1, 19)
    val aquariusFrom = LocalDate.of(2022, 1, 20)
    val aquariusTo = LocalDate.of(2022, 2, 18)
    val piscesFrom = LocalDate.of(2022, 2, 19)
    val piscesInLeap = LocalDate.of(2020, 2, 29)
    val piscesTo = LocalDate.of(2022, 3, 20)

    describe("Sample Based Test") {
        describe("getZodiacSign") {
            it("Aries from") {
                val actual = ZodiacSign.getZodiacSign(ariesFrom)
                actual shouldBe ZodiacSign.Aries
            }
            it("Aries to") {
                val actual = ZodiacSign.getZodiacSign(ariesTo)
                actual shouldBe ZodiacSign.Aries
            }
            it("Taurus from") {
                val actual = ZodiacSign.getZodiacSign(taurusFrom)
                actual shouldBe ZodiacSign.Taurus
            }
            it("Taurus to") {
                val actual = ZodiacSign.getZodiacSign(taurusTo)
                actual shouldBe ZodiacSign.Taurus
            }
            it("Gemini from") {
                val actual = ZodiacSign.getZodiacSign(geminiFrom)
                actual shouldBe ZodiacSign.Gemini
            }
            it("Gemini to") {
                val actual = ZodiacSign.getZodiacSign(geminiTo)
                actual shouldBe ZodiacSign.Gemini
            }
            it("Cancer from") {
                val actual = ZodiacSign.getZodiacSign(cancerFrom)
                actual shouldBe ZodiacSign.Cancer
            }
            it("Cancer to") {
                val actual = ZodiacSign.getZodiacSign(cancerTo)
                actual shouldBe ZodiacSign.Cancer
            }
            it("Leo from") {
                val actual = ZodiacSign.getZodiacSign(leoFrom)
                actual shouldBe ZodiacSign.Leo
            }
            it("Leo to") {
                val actual = ZodiacSign.getZodiacSign(leoTo)
                actual shouldBe ZodiacSign.Leo
            }
            it("Virgo from") {
                val actual = ZodiacSign.getZodiacSign(virgoFrom)
                actual shouldBe ZodiacSign.Virgo
            }
            it("Virgo to") {
                val actual = ZodiacSign.getZodiacSign(virgoTo)
                actual shouldBe ZodiacSign.Virgo
            }
            it("Libra from") {
                val actual = ZodiacSign.getZodiacSign(libraFrom)
                actual shouldBe ZodiacSign.Libra
            }
            it("Libra to") {
                val actual = ZodiacSign.getZodiacSign(libraTo)
                actual shouldBe ZodiacSign.Libra
            }
            it("Scorpius from") {
                val actual = ZodiacSign.getZodiacSign(scorpiusFrom)
                actual shouldBe ZodiacSign.Scorpius
            }
            it("Scorpius to") {
                val actual = ZodiacSign.getZodiacSign(scorpiusTo)
                actual shouldBe ZodiacSign.Scorpius
            }
            it("Sagittarius from") {
                val actual = ZodiacSign.getZodiacSign(sagittariusFrom)
                actual shouldBe ZodiacSign.Sagittarius
            }
            it("Sagittarius to") {
                val actual = ZodiacSign.getZodiacSign(sagittariusTo)
                actual shouldBe ZodiacSign.Sagittarius
            }
            it("Capricorn from") {
                val actual = ZodiacSign.getZodiacSign(capricornFrom)
                actual shouldBe ZodiacSign.Capricorn
            }
            it("Capricorn to") {
                val actual = ZodiacSign.getZodiacSign(capricornTo)
                actual shouldBe ZodiacSign.Capricorn
            }
            it("Aquarius from") {
                val actual = ZodiacSign.getZodiacSign(aquariusFrom)
                actual shouldBe ZodiacSign.Aquarius
            }
            it("Aquarius to") {
                val actual = ZodiacSign.getZodiacSign(aquariusTo)
                actual shouldBe ZodiacSign.Aquarius
            }
            it("Pisces from") {
                val actual = ZodiacSign.getZodiacSign(piscesFrom)
                actual shouldBe ZodiacSign.Pisces
            }
            it("Pisces in leap") {
                val actual = ZodiacSign.getZodiacSign(piscesInLeap)
                actual shouldBe ZodiacSign.Pisces
            }
            it("Pisces to") {
                val actual = ZodiacSign.getZodiacSign(piscesTo)
                actual shouldBe ZodiacSign.Pisces
            }
        }
    }
    describe("Property Based Test") {
        describe("getZodiacSign") {
            it("Aries") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = ariesFrom,
                        to = ariesTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Aries
                }
            }
            it("Taurus") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = taurusFrom,
                        to = taurusTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Taurus
                }
            }
            it("Taurus") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = geminiFrom,
                        to = geminiTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Gemini
                }
            }
            it("Cancer") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = cancerFrom,
                        to = cancerTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Cancer
                }
            }
            it("Leo") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = leoFrom,
                        to = leoTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Leo
                }
            }
            it("Virgo") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = virgoFrom,
                        to = virgoTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Virgo
                }
            }
            it("Libra") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = libraFrom,
                        to = libraTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Libra
                }
            }
            it("Scorpius") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = scorpiusFrom,
                        to = scorpiusTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Scorpius
                }
            }
            it("Sagittarius") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = sagittariusFrom,
                        to = sagittariusTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Sagittarius
                }
            }
            it("Capricorn") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = capricornFrom,
                        to = capricornTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Capricorn
                }
            }
            it("Aquarius") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = aquariusFrom,
                        to = aquariusTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Aquarius
                }
            }
            it("Pisces") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = piscesFrom,
                        to = piscesTo
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Pisces
                }
            }

            it("Pisces in leep") {
                val exBirthDay = UserGenerator.generateExhaustiveBirthday(
                    UserGenerator.BirthDayRange(
                        from = piscesFrom.withYear(2020),
                        to = piscesTo.withYear(2020)
                    )
                )

                checkAll(exBirthDay) { birthDay ->
                    val actual = ZodiacSign.getZodiacSign(birthDay)
                    actual shouldBe ZodiacSign.Pisces
                }
            }
        }
    }
})