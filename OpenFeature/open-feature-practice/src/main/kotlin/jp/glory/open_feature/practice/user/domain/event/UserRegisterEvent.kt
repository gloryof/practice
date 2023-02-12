package jp.glory.open_feature.practice.user.domain.event

import jp.glory.open_feature.practice.user.domain.model.BirthDay
import jp.glory.open_feature.practice.user.domain.model.UserId
import jp.glory.open_feature.practice.user.domain.model.UserName

class UserRegisterEvent(
    val id: UserId,
    val name: UserName,
    val birthDay: BirthDay,
    val password: InputPassword
)

@JvmInline
value class InputPassword(val value: String)