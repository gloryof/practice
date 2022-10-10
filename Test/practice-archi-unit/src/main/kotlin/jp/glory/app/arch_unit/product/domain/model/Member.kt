package jp.glory.app.arch_unit.product.domain.model

import java.time.LocalDate

data class Member(
    val id: MemberID,
    val name: MemberName,
    val birthDay: LocalDate
)

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
