package jp.glory.practice.grpc.user.adaptor.store

import jp.glory.practice.grpc.user.adaptor.store.db.UserDao
import jp.glory.practice.grpc.user.adaptor.store.db.UserRecord
import jp.glory.practice.grpc.user.domain.event.RegisterUserEvent
import jp.glory.practice.grpc.user.domain.event.RegisterUserEventHandler
import org.springframework.stereotype.Component

@Component
class RegisterUserEventHandlerImpl(
    private val userDao: UserDao
) : RegisterUserEventHandler {
    override fun register(event: RegisterUserEvent) {
        toRecord(event)
            .let { userDao.save(it) }
    }

    private fun toRecord(event: RegisterUserEvent): UserRecord =
        UserRecord(
            id = event.userId.value,
            userName = event.userName.value,
            birthday = event.birthday.value
        )
}