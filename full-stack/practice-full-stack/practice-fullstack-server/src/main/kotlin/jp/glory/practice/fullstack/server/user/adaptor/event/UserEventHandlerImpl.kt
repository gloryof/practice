package jp.glory.practice.fullstack.server.user.adaptor.event

import jp.glory.practice.fullstack.server.base.adaptor.store.AuthDao
import jp.glory.practice.fullstack.server.base.adaptor.store.AuthRecord
import jp.glory.practice.fullstack.server.base.adaptor.store.UserDao
import jp.glory.practice.fullstack.server.base.adaptor.store.UserRecord
import jp.glory.practice.fullstack.server.user.domain.RegisterUserEvent
import jp.glory.practice.fullstack.server.user.domain.UpdateUserEvent
import jp.glory.practice.fullstack.server.user.domain.UserEventHandler
import org.springframework.stereotype.Component

@Component
class UserEventHandlerImpl(
    private val userDao: UserDao,
    private val authDao: AuthDao
) : UserEventHandler {
    override fun register(event: RegisterUserEvent) {
        val userRecord = UserRecord(
            id =  event.id.value,
            name = event.name.value,
            birthday = event.birthday.value
        )
        val authRecord = AuthRecord(
            userId = event.id.value,
            password = event.password.value
        )

        userDao.save(userRecord)
        authDao.save(authRecord)
    }

    override fun update(event: UpdateUserEvent) {
        val userRecord = UserRecord(
            id =  event.id.value,
            name = event.name.value,
            birthday = event.birthday.value
        )

        userDao.save(userRecord)
    }
}