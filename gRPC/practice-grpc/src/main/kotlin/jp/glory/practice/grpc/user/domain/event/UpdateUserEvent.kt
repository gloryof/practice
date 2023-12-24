package jp.glory.practice.grpc.user.domain.event

import jp.glory.practice.grpc.user.domain.model.Birthday
import jp.glory.practice.grpc.user.domain.model.UserId
import jp.glory.practice.grpc.user.domain.model.UserName

class UpdateUserEvent private constructor(
    val userId: UserId,
    val userName: UserName,
    val birthday: Birthday
) {
    companion object {
        fun create(
            userId: UserId,
            userName: UserName,
            birthday: Birthday
        ): UpdateUserEvent =
            UpdateUserEvent(
                userId = userId,
                userName =  userName,
                birthday = birthday
            )
    }
}


interface UpdateUserEventHandler {
    fun update(event: UpdateUserEvent)
}