package jp.glory.practice.grpc.user.domain.model

import jp.glory.practice.grpc.user.domain.event.UpdateUserEvent
import java.time.LocalDate
import java.util.UUID

class User(
    val id: UserId,
    val name: UserName,
    val birthday: Birthday
) {
    fun update(
        newName: UserName = name,
        newBirthday: Birthday = birthday
    ): UpdateUserEvent =
        UpdateUserEvent.create(
            userId = id,
            userName = newName,
            birthday = newBirthday
        )
}

@JvmInline
value class UserId(val value: String) {
    init {
        assert(value.isNotBlank())
    }
}

object UserIdGenerator {
    fun generate(): UserId =
        UserId(UUID.randomUUID().toString())
}

@JvmInline
value class UserName(val value: String) {

    init {
        assert(value.isNotBlank())
        assert(value.length <= 100)
    }
}

@JvmInline
value class Birthday(val value: LocalDate) {
    init {
        assert(LocalDate.now().isAfter(value))
    }
}