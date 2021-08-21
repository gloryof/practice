package jp.glory.domain

import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.Gen
import io.kotest.property.arbitrary.*
import io.kotest.property.exhaustive.exhaustive
import java.time.LocalDate

object UserGenerator {
    fun generate(
        id: Gen<UserId> = generateId(),
        name: Gen<Name> = NameGenerator.generate(),
        birthDay: Gen<LocalDate> = BirthDayGenerator.generate(LocalDate.now())
    ): Arb<User> = Arb.bind(
        id,
        name,
        birthDay
    ) {
        id,
        name,
        birthDay ->
        User(
            id = id,
            name = name,
            birthDay = birthDay
        )
    }

    fun generateId(): Arb<UserId> = Arb.string().map { UserId(it) }

}

object NameGenerator {
    fun generate(): Arb<Name> =
        Arb.bind(
            Arb.string(
                codepoints = Arb.alphanumeric()
            ),
            Arb.string(
                codepoints = Arb.alphanumeric()
            )
        ) {
            familyName,
            givenName ->
            Name(
                familyName = familyName,
                givenName = givenName
            )
        }
}

object BirthDayGenerator {
    fun generate(
        baseDate: LocalDate = LocalDate.now()
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = LocalDate.of(1850, 1, 1),
                to = baseDate
            )
        )

    fun generateUnder10BirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(10).plusDays(1),
                to = baseDate
            )
        )

    fun generateAge10sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(20).plusDays(1),
                to = baseDate.minusYears(10),
            )
        )

    fun generateAge20sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(30).plusDays(1),
                to = baseDate.minusYears(20),
            )
        )

    fun generateAge30sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(40).plusDays(1),
                to = baseDate.minusYears(30),
            )
        )

    fun generateAge40sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(50).plusDays(1),
                to = baseDate.minusYears(40),
            )
        )

    fun generateAge50sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(60).plusDays(1),
                to = baseDate.minusYears(50),
            )
        )

    fun generateOver60sBirthDay(
        baseDate: LocalDate
    ): Arb<LocalDate> =
        generateArbBirthDay(
            range = BirthDayRange(
                from = baseDate.minusYears(200),
                to = baseDate.minusYears(60),
            )
        )

    fun generateExhaustiveBirthday(
        range: BirthDayRange
    ): Exhaustive<LocalDate> {
        val diffs = range.from.until(range.to).days

        if (diffs < 0) {
            return emptyList<LocalDate>().exhaustive()
        }

        val values = mutableListOf<LocalDate>()
        for (i in 0 .. diffs) {
            values.add(range.from.plusDays(i.toLong()))
        }

        return values.exhaustive()
    }

    private fun generateArbBirthDay(
        range: BirthDayRange
    ): Arb<LocalDate> = Arb.localDate(range.from, range.to)

    class BirthDayRange(
        val from: LocalDate,
        val to: LocalDate
    )
}