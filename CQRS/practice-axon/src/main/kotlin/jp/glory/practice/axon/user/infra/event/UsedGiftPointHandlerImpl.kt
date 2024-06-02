package jp.glory.practice.axon.user.infra.event

import jp.glory.practice.axon.user.domain.event.UsedGiftPoint
import jp.glory.practice.axon.user.domain.event.UsedGiftPointHandler
import jp.glory.practice.axon.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class UsedGiftPointHandlerImpl(
    private val userDao: UserDao
) : UsedGiftPointHandler {
    override fun handle(event: UsedGiftPoint) {
        userDao.findById(event.userId.value)
            ?.let {
                it.copy(giftPoint = it.giftPoint - event.useAmount)
            }
            ?.let { userDao.save(it) }
            ?: throw IllegalArgumentException("Not found user")
    }
}