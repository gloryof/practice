package jp.glory.oauth.practice.resource.context.user.usecase

import jp.glory.oauth.practice.resource.base.system.UUIDGenerator
import jp.glory.oauth.practice.resource.base.usecase.UseCase
import jp.glory.oauth.practice.resource.context.user.domain.event.UserRegisterEvent
import jp.glory.oauth.practice.resource.context.user.domain.model.UserIdGenerator
import jp.glory.oauth.practice.resource.context.user.domain.model.UserName
import jp.glory.oauth.practice.resource.context.user.domain.repository.UserRegisterEventRepository

@UseCase
class RegisterUser(
    private val repositoryRegister: UserRegisterEventRepository,
    private val generator: UUIDGenerator
) {
    fun register(
        name: String,
        plainPassword: String
    ): Result {
        val event = createEvent(
            name = name,
            plainPassword = plainPassword
        )
        repositoryRegister.save(event)

        return Result(event.userId.value)
    }

    private fun createEvent(
        name: String,
        plainPassword: String
    ): UserRegisterEvent =
        UserRegisterEvent(
            userId = UserIdGenerator(generator).generate(),
            name = UserName(name),
            plainPassword = plainPassword
        )

    data class Result(val id: String)
}

