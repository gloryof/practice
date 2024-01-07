package jp.glory.practice.arrow.basic.immutable.usecase

import jp.glory.practice.arrow.basic.immutable.model.User
import jp.glory.practice.arrow.basic.immutable.model.UserId
import jp.glory.practice.arrow.basic.immutable.model.UserRepository
import jp.glory.practice.arrow.basic.immutable.model.userId
import java.time.LocalDate

class RegisterUser(
    private val repository: UserRepository
) {

    fun register(input: Input): UserId =
        User.create(
            userName = input.userName,
            birthday = input.birthday
        )
            .also { repository.save(it) }
            .let { User.userId.get(it) }

    class Input(
        val userName: String,
        val birthday: LocalDate,
    )
}