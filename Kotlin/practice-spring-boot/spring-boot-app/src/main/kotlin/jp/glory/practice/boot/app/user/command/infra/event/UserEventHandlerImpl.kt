package jp.glory.practice.boot.app.user.command.infra.event

import jp.glory.practice.boot.app.auth.data.AuthDao
import jp.glory.practice.boot.app.auth.data.AuthRecord
import jp.glory.practice.boot.app.user.command.domain.event.UserCreated
import jp.glory.practice.boot.app.user.command.domain.event.UserEventHandler
import jp.glory.practice.boot.app.user.data.UserDao
import jp.glory.practice.boot.app.user.data.UserRecord

class UserEventHandlerImpl(
    private val userDao: UserDao,
    private val authDao: AuthDao
) : UserEventHandler {
    override fun handleUserCreated(event: UserCreated) {
        toUserRecord(event).let { userDao.insert(it) }
        toAuthRecord(event).let { authDao.insert(it) }
    }

    private fun toUserRecord(event: UserCreated): UserRecord =
        UserRecord(
            userId = event.userId.value,
            userName = event.userName.value,
            birthday = event.birthday.value
        )

    private fun toAuthRecord(event: UserCreated): AuthRecord =
        AuthRecord(
            loginId = event.loginId.value,
            userId = event.userId.value,
            password = event.password.value
        )
}
