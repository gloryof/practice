package jp.glory.oauth.practice.resource.context.user.domain.event

import jp.glory.oauth.practice.resource.context.user.domain.model.UserId
import jp.glory.oauth.practice.resource.context.user.domain.model.UserName

data class UserUpdateEvent(
    val userId: UserId,
    val name: UserName
)