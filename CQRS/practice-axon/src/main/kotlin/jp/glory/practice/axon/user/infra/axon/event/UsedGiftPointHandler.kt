package jp.glory.practice.axon.user.infra.axon.event

import jp.glory.practice.axon.user.domain.event.UsedGiftPoint
import jp.glory.practice.axon.user.infra.store.UserDao
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class UsedGiftPointHandler(
    private val userDao: UserDao
) {
    @EventHandler
    fun handle(event: UsedGiftPoint) {
        userDao.findById(event.userId.value)
            ?.let {
                it.copy(giftPoint = it.giftPoint - event.useAmount)
            }
            ?.let { userDao.save(it) }
            ?: throw IllegalArgumentException("Not found user")
    }
}