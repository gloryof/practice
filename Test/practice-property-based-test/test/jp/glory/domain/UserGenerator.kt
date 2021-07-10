package jp.glory.domain

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import java.time.LocalDate

object UserGenerator {
    fun generate(
        id: Arb<UserId> = generateId(),
        birthDay: Arb<LocalDate> = generateArbBirthDay(LocalDate.now())
    ): Arb<User> = Arb.bind(
        id,
        birthDay
    ) {
        id,
        birthDay ->
        User(id, birthDay)
    }

    private fun generateId(): Arb<UserId> = Arb.string().map { UserId(it) }
    private fun generateArbBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = LocalDate.of(1850, 1, 1),
                max = baseDate
            )
        )

    fun generateUnder10BirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(10).plusDays(1),
                max = baseDate
            )
        )

    fun generateAge10sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(20).plusDays(1),
                max = baseDate.minusYears(10),
            )
        )

    fun generateAge20sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(30).plusDays(1),
                max = baseDate.minusYears(20),
            )
        )

    fun generateAge30sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(40).plusDays(1),
                max = baseDate.minusYears(30),
            )
        )

    fun generateAge40sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(50).plusDays(1),
                max = baseDate.minusYears(40),
            )
        )

    fun generateAge50sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(60).plusDays(1),
                max = baseDate.minusYears(50),
            )
        )

    fun generateOver60sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateBirthDay(
            range = BirthDayRange(
                min = baseDate.minusYears(200),
                max = baseDate.minusYears(60),
            )
        )

    private fun generateBirthDay(
        range: BirthDayRange
    ): Arb<LocalDate> =Arb.localDate(range.min, range.max)

    private class BirthDayRange(
        val min: LocalDate,
        val max: LocalDate
    )
}