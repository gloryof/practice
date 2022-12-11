package jp.glory.boot3practice.user.domain.model

import java.time.LocalDate
import java.util.UUID

class User(
    val name: UserName,
    val birthDay: BirthDay
)

@JvmInline
value class UserId constructor(val value: String) {
    companion object {
        fun generate(): UserId = UserId(UUID.randomUUID().toString())
    }
}

@JvmInline
value class UserName(val value: String)

@JvmInline
value class BirthDay(val value: LocalDate)