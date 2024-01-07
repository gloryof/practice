package jp.glory.practice.arrow.basic.immutable

import jp.glory.practice.arrow.basic.immutable.model.User
import jp.glory.practice.arrow.basic.immutable.model.UserId
import jp.glory.practice.arrow.basic.immutable.model.UserRepository
import jp.glory.practice.arrow.basic.immutable.model.UserStatus
import jp.glory.practice.arrow.basic.immutable.model.birthday
import jp.glory.practice.arrow.basic.immutable.model.status
import jp.glory.practice.arrow.basic.immutable.model.userId
import jp.glory.practice.arrow.basic.immutable.model.userName
import jp.glory.practice.arrow.basic.immutable.model.value
import java.time.LocalDate

class LensesPractice(
    private val repository: UserRepository
) {
    fun getById(userId: String): UserResult? =
        repository
            .findByUserId(UserId(userId))
            ?.let { toResult(it) }

    fun register(input: RegisterInput): UserId =
        User.create(
            userName = input.userName,
            birthday = input.birthday
        )
            .also { repository.save(it) }
            .let { User.userId.get(it) }

    private fun toResult(user: User): UserResult =
        UserResult(
            userId = User.userId.value.get(user),
            userName = User.userName.value.get(user),
            birthday = User.birthday.value.get(user),
            status = User.status.get(user).let { UserResult.Status.toStatus(it) }
        )

    class UserResult(
        val userId: String,
        val userName: String,
        val birthday: LocalDate,
        val status: Status
    ) {
        enum class Status {
            Active,
            Suspended;

            companion object {
                fun toStatus(value: UserStatus): Status =
                    when (value) {
                        UserStatus.Active -> Active
                        UserStatus.Suspended -> Suspended
                    }
            }
        }
    }

    class RegisterInput(
        val userName: String,
        val birthday: LocalDate,
    )
}