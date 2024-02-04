package jp.glory.practice.fullstack.server.user.usecase

import jp.glory.practice.fullstack.server.base.usecase.ExecuteUser
import jp.glory.practice.fullstack.server.user.domain.Birthday
import jp.glory.practice.fullstack.server.user.domain.UserEventHandler
import jp.glory.practice.fullstack.server.user.domain.UserId
import jp.glory.practice.fullstack.server.user.domain.UserName
import jp.glory.practice.fullstack.server.user.domain.UserRepository
import java.time.LocalDate

class UpdateUser(
    private val repository: UserRepository,
    private val userEventHandler: UserEventHandler
) {
    fun update(
        executeUser: ExecuteUser,
        input: Input
    ): Output =
        repository
            .findById(UserId(executeUser.userId))
            ?.let {
                it.update(
                    name = UserName(input.name),
                    birthday = Birthday(input.birthday)
                )
            }
            ?.also { userEventHandler.update(it) }
            ?.let { Output(it.id.value) }
            ?: throw IllegalStateException("Update is failed")

    class Input(
        val name: String,
        val birthday: LocalDate,
    )

    class Output(
        val registeredId: String,
    )
}