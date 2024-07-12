package jp.glory.practice.axon.user.infra.axon.event

import jp.glory.practice.axon.user.domain.event.ChangedName
import jp.glory.practice.axon.user.infra.store.UserDao
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class ChangedNameHandlerImpl(
    private val userDao: UserDao
) {
    @EventHandler
    fun on(event: ChangedName) {
        userDao.findById(event.userId.value)
            ?.let { it.copy(userName = event.name.value) }
            ?.let { userDao.save(it) }
            ?: throw IllegalArgumentException("Not found user")
    }
}