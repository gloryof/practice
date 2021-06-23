package jp.glory.domain

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import java.time.LocalDate

object UserGenerator {
    private val idGen: Arb<UserId> = Arb.string().map { UserId(it) }
    fun generateUnder10(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(10).plusDays(1),
                max = baseDate
            )
        )

    fun generateAge10s(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(20).plusDays(1),
                max = baseDate.minusYears(10),
            )
        )

    fun generateAge20s(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(30).plusDays(1),
                max = baseDate.minusYears(20),
            )
        )

    fun generateAge30s(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(40).plusDays(1),
                max = baseDate.minusYears(30),
            )
        )

    fun generateAge40s(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(50).plusDays(1),
                max = baseDate.minusYears(40),
            )
        )

    fun generateAge50s(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(60).plusDays(1),
                max = baseDate.minusYears(50),
            )
        )

    fun generateOver60s(
        baseDate: LocalDate
    ): Arb<User> =
        generate(
            range = BirthDayRange(
                min = baseDate.minusYears(200),
                max = baseDate.minusYears(60),
            )
        )

    private fun generate(
        range: BirthDayRange
    ): Arb<User> = Arb.bind(
        idGen,
        Arb.localDate(range.min, range.max)
    ) { id,
        birthDay ->
        User(
            id = id,
            birthDay = birthDay
        )
    }

    private class BirthDayRange(
        val min: LocalDate,
        val max: LocalDate
    )
}