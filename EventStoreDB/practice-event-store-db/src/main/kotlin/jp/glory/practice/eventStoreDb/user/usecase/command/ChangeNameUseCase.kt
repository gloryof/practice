package jp.glory.practice.eventStoreDb.user.usecase.command

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedNameHandler
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.model.UserName
import jp.glory.practice.eventStoreDb.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ChangeNameUseCase(
    private val repository: UserRepository,
    private val handler: ChangedNameHandler
) {
    fun change(input: Input) {
        repository.findById(UserId.fromString(input.userId))
            ?.also { handler.handle(it.changeName(UserName(input.name))) }
            ?: throw IllegalArgumentException("User not found")
    }

    class Input(
        val userId: String,
        val name: String,
    )

}