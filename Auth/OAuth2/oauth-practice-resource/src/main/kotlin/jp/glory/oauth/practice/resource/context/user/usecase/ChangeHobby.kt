package jp.glory.oauth.practice.resource.context.user.usecase

import jp.glory.oauth.practice.resource.base.usecase.UseCase
import jp.glory.oauth.practice.resource.context.user.domain.event.ChangeHobbiesEvent
import jp.glory.oauth.practice.resource.context.user.domain.model.Hobby
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId
import jp.glory.oauth.practice.resource.context.user.domain.repository.ChangeHobbiesEventRepository

@UseCase
class ChangeHobby(
    private val repository: ChangeHobbiesEventRepository
) {
    fun change(
        userId: String,
        hobbies: List<String>
    ) =
        ChangeHobbiesEvent(
            userId = UserId(userId),
            hobbies = hobbies.map { Hobby(it) }
        )
            .let { repository.save(it) }
}