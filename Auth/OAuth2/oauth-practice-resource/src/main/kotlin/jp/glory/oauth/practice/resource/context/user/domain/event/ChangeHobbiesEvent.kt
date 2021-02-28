package jp.glory.oauth.practice.resource.context.user.domain.event

import jp.glory.oauth.practice.resource.context.user.domain.model.Hobby
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId

data class ChangeHobbiesEvent(
    val userId: UserId,
    val hobbies: List<Hobby>
)