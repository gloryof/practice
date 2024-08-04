package jp.glory.practice.eventStoreDb.user.domain.event

import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.model.UserName

class ChangedName(
    val userId: UserId,
    val name: UserName,
)

interface ChangedNameHandler {
    fun handle(event: ChangedName)
}