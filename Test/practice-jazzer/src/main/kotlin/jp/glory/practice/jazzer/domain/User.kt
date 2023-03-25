package jp.glory.practice.jazzer.domain

import java.time.LocalDate
import java.time.MonthDay
import java.time.temporal.ChronoUnit

data class User(
    val id: UserId,
    val name: Name,
    val birthDay: LocalDate
) {
    val zodiacSign: ZodiacSign = ZodiacSign.getZodiacSign(birthDay)
    fun calculateAge(
        targetDate: LocalDate
    ): Age = Age(ChronoUnit.YEARS.between(birthDay, targetDate).toInt())
}

@JvmInline
value class UserId(val value: String)

data class Name(
    val familyName: String,
    val givenName: String
) {
    fun getJapanStyle(): String = "$familyName $givenName"
    fun getForeignStyle(): String = "$givenName $familyName"
}

data class Age(
    val value: Int
) {
    val group: Group = when(value / 10) {
        0 -> Group.Under10
        1 -> Group.Age10s
        2 -> Group.Age20s
        3 -> Group.Age30s
        4 -> Group.Age40s
        5 -> Group.Age50s
        else -> Group.Over60
    }

    enum class Group {
        Under10,
        Age10s,
        Age20s,
        Age30s,
        Age40s,
        Age50s,
        Over60
    }
}

enum class ZodiacSign(
    private val ranges: List<Range>
) {
    Aries(
        listOf(
            Range(
                from = MonthDay.of(3, 21),
                to = MonthDay.of(4, 19)
            )
        )
    ),
    Taurus(
        listOf(
            Range(
                from = MonthDay.of(4, 20),
                to = MonthDay.of(5, 20)
            )
        )
    ),
    Gemini(
        listOf(
            Range(
                from = MonthDay.of(5, 21),
                to = MonthDay.of(6, 21)
            )
        )
    ),
    Cancer(
        listOf(
            Range(
                from = MonthDay.of(6, 22),
                to = MonthDay.of(7, 22)
            )
        )
    ),
    Leo(
        listOf(
            Range(
                from = MonthDay.of(7, 23),
                to = MonthDay.of(8, 22)
            )
        )
    ),
    Virgo(
        listOf(
            Range(
                from = MonthDay.of(8, 23),
                to = MonthDay.of(9, 22)
            )
        )
    ),
    Libra(
        listOf(
            Range(
                from = MonthDay.of(9, 23),
                to = MonthDay.of(10, 23)
            )
        )
    ),
    Scorpius(
        listOf(
            Range(
                from = MonthDay.of(10, 24),
                to = MonthDay.of(11, 22)
            )
        )
    ),
    Sagittarius(
        listOf(
            Range(
                from = MonthDay.of(11, 23),
                to = MonthDay.of(12,21)
            )
        )
    ),
    Capricorn(
        listOf(
            Range(
                from = MonthDay.of(1, 1),
                to = MonthDay.of(1,19)
            ),
            Range(
                from = MonthDay.of(12, 22),
                to = MonthDay.of(12,31)
            )
        )
    ),
    Aquarius(
        listOf(
            Range(
                from = MonthDay.of(1, 20),
                to = MonthDay.of(2,18)
            )
        )
    ),
    Pisces(
        listOf(
            Range(
                from = MonthDay.of(2, 19),
                to = MonthDay.of(3,20)
            )
        )
    );

    companion object {
        fun getZodiacSign(birthDay: LocalDate): ZodiacSign =
            values()
                .first { sign ->
                    sign.ranges.any { it.contains(birthDay) }
                }
    }

    private class Range(
        private val from: MonthDay,
        private val to: MonthDay,
    ) {
        fun contains(date: LocalDate): Boolean {
            var target = MonthDay.of(date.month, date.dayOfMonth)

            if (target.isBefore(from)) {
                return false
            }

            if (target.isAfter(to)) {
                return false
            }

            return true
        }
    }
}