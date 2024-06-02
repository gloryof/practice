package jp.glory.practice.axon.user.infra.event

import jp.glory.practice.axon.user.domain.event.ChangedName
import jp.glory.practice.axon.user.domain.event.ChangedNameHandler
import jp.glory.practice.axon.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class ChangedNameHandlerImpl(
    private val userDao: UserDao
) : ChangedNameHandler {
    override fun handle(event: ChangedName) {
        userDao.findById(event.userId.value)
            ?.let { it.copy(userName = event.name.value) }
            ?.let { userDao.save(it) }
            ?: throw IllegalArgumentException("Not found user")
    }
}