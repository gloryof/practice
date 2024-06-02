package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.event.ChangedNameHandler
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ChangeNameUseCase(
    private val userRepository: UserRepository,
    private val changedNameHandler: ChangedNameHandler
) {
    fun change(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.changeName(UserName(input.name)) }
            ?.let { changedNameHandler.handle(it) }
            ?: throw IllegalArgumentException("Use not found")

    class Input(
        val userId: String,
        val name: String,
    )

}