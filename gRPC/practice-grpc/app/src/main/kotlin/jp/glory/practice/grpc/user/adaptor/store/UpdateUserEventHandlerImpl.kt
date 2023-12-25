package jp.glory.practice.grpc.user.adaptor.store

import jp.glory.practice.grpc.user.adaptor.store.db.UserDao
import jp.glory.practice.grpc.user.adaptor.store.db.UserRecord
import jp.glory.practice.grpc.user.domain.event.UpdateUserEvent
import jp.glory.practice.grpc.user.domain.event.UpdateUserEventHandler
import org.springframework.stereotype.Component

@Component
class UpdateUserEventHandlerImpl(
    private val userDao: UserDao
) : UpdateUserEventHandler {

    override fun update(event: UpdateUserEvent) {
        toRecord(event)
            .let { userDao.save(it) }
    }

    private fun toRecord(event: UpdateUserEvent): UserRecord =
        UserRecord(
            id = event.userId.value,
            userName = event.userName.value,
            birthday = event.birthday.value
        )

}