package jp.glory.practice.measure.architecture.product.domain.model

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Member(
    val id: MemberID,
    val name: MemberName,
    val birthDay: LocalDate
) {
    fun calculateAge(
        today: LocalDate
    ): Int = ChronoUnit.YEARS.between(birthDay, today).toInt()
}

data class MemberName(
    val givenName: GivenName,
    val familyName: FamilyName
)

@JvmInline
value class MemberID(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class GivenName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 50)
    }
}

@JvmInline
value class FamilyName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 50)
    }
}
