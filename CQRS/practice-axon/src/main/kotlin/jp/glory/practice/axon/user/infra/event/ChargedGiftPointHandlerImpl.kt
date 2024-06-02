package jp.glory.practice.axon.user.infra.event

import jp.glory.practice.axon.user.domain.event.ChargedGiftPoint
import jp.glory.practice.axon.user.domain.event.ChargedGiftPointHandler
import jp.glory.practice.axon.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class ChargedGiftPointHandlerImpl(
    private val userDao: UserDao
) : ChargedGiftPointHandler {
    override fun handle(event: ChargedGiftPoint) {
        userDao.findById(event.userId.value)
            ?.let {
                it.copy(giftPoint = it.giftPoint + event.chargeAmount)
            }
            ?.let { userDao.save(it) }
            ?: throw IllegalArgumentException("Not found user")
    }
}