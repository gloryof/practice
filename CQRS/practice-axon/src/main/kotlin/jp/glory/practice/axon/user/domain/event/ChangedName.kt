package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName

class ChangedName(
    val userId: UserId,
    val name: UserName,
)