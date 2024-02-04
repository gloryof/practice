package jp.glory.practice.fullstack.server.user.usecase

import jp.glory.practice.fullstack.server.base.exception.NotFoundException
import jp.glory.practice.fullstack.server.user.domain.UserId
import jp.glory.practice.fullstack.server.user.domain.UserRepository
import java.time.LocalDate

class GetUser(
    private val repository: UserRepository
) {
    fun getById(input: Input): Output =
        repository.findById(UserId(input.userId))
            ?.let {
                Output(
                    id = it.id.value,
                    name = it.name.value,
                    birthday = it.birthday.value
                )
            }
            ?: throw NotFoundException("User Not found")

    class Input(
        val userId: String
    )

    class Output(
        val id: String,
        val name: String,
        val birthday: LocalDate
    )
}