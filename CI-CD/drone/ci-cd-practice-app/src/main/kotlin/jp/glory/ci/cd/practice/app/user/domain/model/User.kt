package jp.glory.ci.cd.practice.app.user.domain.model

data class User(
    val userId: UserId,
    val givenName: GivenName,
    val familyName: FamilyName
)

interface UserIdGenerator {
    fun generate(): UserId
}

@JvmInline
value class UserId(val value: String) {
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

class Password(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length >= 20)
    }
}