package jp.glory.practice.eventStoreDb.user.infra.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.UsedGiftPoint
import jp.glory.practice.eventStoreDb.user.domain.event.UsedGiftPointHandler
import jp.glory.practice.eventStoreDb.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class UsedGiftPointHandlerImpl(
    private val dao: UserDao
) : UsedGiftPointHandler {
    override fun handle(event: UsedGiftPoint): Unit =
        dao.findById(event.userId.value)
            ?.let {
                it.copy(
                    giftPoint = it.giftPoint - event.useAmount
                )
            }
            ?.let { dao.save(it) }
            ?: throw IllegalStateException("User not found")

}