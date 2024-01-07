package jp.glory.practice.arrow.basic.immutable.usecase

import jp.glory.practice.arrow.basic.immutable.model.Birthday
import jp.glory.practice.arrow.basic.immutable.model.UserId
import jp.glory.practice.arrow.basic.immutable.model.UserName
import jp.glory.practice.arrow.basic.immutable.model.UserRepository
import java.time.LocalDate

class ChangeProfile(
    private val repository: UserRepository
) {

    fun change(input: Input) {
        repository.findByUserId(UserId(input.userId))
            ?.let {
                it.changeProfile(
                    userName = UserName(input.userName),
                    birthday = Birthday(input.birthday)
                )
            }
            ?.let { repository.save(it) }
            ?: throw IllegalStateException("User not found")
    }

    class Input(
        val userId: String,
        val userName: String,
        val birthday: LocalDate,
    )
}