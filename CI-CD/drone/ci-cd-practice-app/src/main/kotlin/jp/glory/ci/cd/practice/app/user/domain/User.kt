package jp.glory.ci.cd.practice.app.user.domain

data class User(
    val givenName: GivenName,
    val familyName: FamilyName
)

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