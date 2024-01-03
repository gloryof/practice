package jp.glory.practice.arrow.basic.immutable.model


import arrow.optics.optics
import java.time.LocalDate

@optics
data class User(
    val userId: UserId,
    val userName: UserName,
    val birthday: Birthday,
    val status: UserStatus
) {

    companion object
}


@JvmInline
@optics
value class UserId(val value: String) {

    init {
        assert(value.isNotBlank())
    }

    companion object
}

@JvmInline
@optics
value class UserName(val value: String) {
    init {
        assert(value.isNotBlank())
        assert(value.length <= 100)
    }

    companion object
}

@JvmInline
@optics
value class Birthday(val value: LocalDate) {
    init {
        val today = LocalDate.now()
        assert(value.isBefore(today) || value == today)
    }

    companion object
}


enum class UserStatus {
    Active,
    Suspended;

    companion object
}


interface UserRepository {
    fun findByUserId(userId: UserId): User?
    fun save(user: User)
}

class UserRepositoryImpl(
    private val users: MutableMap<UserId, User> = mutableMapOf()
) : UserRepository {
    override fun findByUserId(userId: UserId): User? = users[userId]

    override fun save(user: User) {
        users[user.userId] = user
    }
}