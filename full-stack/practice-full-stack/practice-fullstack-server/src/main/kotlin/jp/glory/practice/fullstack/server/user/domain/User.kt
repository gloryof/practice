package jp.glory.practice.fullstack.server.user.domain

import java.time.LocalDate
import java.util.UUID

class User(
    val id: UserId,
    val name: UserName,
    val birthday: Birthday
) {
    fun update(
        name: UserName,
        birthday: Birthday
    ): UpdateUserEvent =
        UpdateUserEvent(
            id =  id,
            name = name,
            birthday = birthday
        )
}

@JvmInline
value class UserId(val value: String) {
    companion object {
        fun generate(): UserId =
            UserId(UUID.randomUUID().toString())
    }
    init {
        assert(value.isNotBlank())
    }
}

@JvmInline
value class UserName(val value: String){
    init {
        assert(value.isNotBlank())
        assert(value.length < 100)
    }
}

@JvmInline
value class Birthday(val value: LocalDate){
    init {
        val today = LocalDate.now()
        assert(value == today || value.isBefore(today))
    }
}

@JvmInline
value class RegisterPassword(val value: String){
    init {
        assert(value.isNotBlank())
    }
}

class RegisterUserEvent private constructor(
    val id: UserId,
    val name: UserName,
    val birthday: Birthday,
    val password: RegisterPassword
) {
    companion object {
        fun create(
            name: String,
            birthday: LocalDate,
            password: String
        ): RegisterUserEvent =
            RegisterUserEvent(
                id = UserId.generate(),
                name = UserName(name),
                birthday = Birthday(birthday),
                password = RegisterPassword(password)
            )
    }
}

class UpdateUserEvent(
    val id: UserId,
    val name: UserName,
    val birthday: Birthday
)


interface UserRepository {
    fun findById(id: UserId): User?
}

interface UserEventHandler {
    fun register(event: RegisterUserEvent)
    fun update(event: UpdateUserEvent)
}