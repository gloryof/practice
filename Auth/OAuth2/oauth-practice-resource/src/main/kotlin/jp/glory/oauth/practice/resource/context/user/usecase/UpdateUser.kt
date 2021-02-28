package jp.glory.oauth.practice.resource.context.user.usecase

import jp.glory.oauth.practice.resource.base.usecase.UseCase
import jp.glory.oauth.practice.resource.context.user.domain.event.UserUpdateEvent
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId
import jp.glory.oauth.practice.resource.context.user.domain.model.UserName
import jp.glory.oauth.practice.resource.context.user.domain.repository.UserUpdateEventRepository

@UseCase
class UpdateUser(
    private val repositoryRegister: UserUpdateEventRepository
) {
    fun update(
        id: String,
        name: String
    ) {
        val event = createEvent(
            id = id,
            name = name,
        )
        repositoryRegister.save(event)
    }

    private fun createEvent(
        id: String,
        name: String
    ): UserUpdateEvent =
        UserUpdateEvent(
            userId = UserId(id),
            name = UserName(name)
        )
}