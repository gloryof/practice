package jp.glory.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class User(
    val id: UserId,
    val birthDay: LocalDate
) {
    fun calculateAge(
        targetDate: LocalDate
    ): Age = Age(ChronoUnit.YEARS.between(birthDay, targetDate).toInt())
}

@JvmInline
value class UserId(val value: String)

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

