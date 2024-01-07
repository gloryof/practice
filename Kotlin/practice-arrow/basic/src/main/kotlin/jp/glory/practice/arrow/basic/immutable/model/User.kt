package jp.glory.practice.arrow.basic.immutable.model


import arrow.optics.copy
import arrow.optics.optics
import java.time.LocalDate
import java.util.UUID

@optics
data class User(
    val userId: UserId,
    val userName: UserName,
    val birthday: Birthday,
    val status: UserStatus
) {

    companion object {
        fun create(
            userName: String,
            birthday: LocalDate,
        ): User =
            User(
                userId = UserId.generate(),
                userName = UserName(userName),
                birthday = Birthday(birthday),
                status = UserStatus.Active
            )
    }

    fun changeProfile(
        userName: UserName,
        birthday: Birthday
    ): User =
        this.copy {
            User.userName set userName
            User.birthday set birthday
        }
}


@JvmInline
@optics
value class UserId(val value: String) {

    init {
        assert(value.isNotBlank())
    }

    companion object {
        fun generate(): UserId = UserId(UUID.randomUUID().toString())
    }
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