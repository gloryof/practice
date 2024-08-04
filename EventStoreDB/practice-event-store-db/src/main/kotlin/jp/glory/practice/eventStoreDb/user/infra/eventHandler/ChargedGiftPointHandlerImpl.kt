package jp.glory.practice.eventStoreDb.user.infra.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddress
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddressHandler
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedName
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedNameHandler
import jp.glory.practice.eventStoreDb.user.domain.event.ChargedGiftPoint
import jp.glory.practice.eventStoreDb.user.domain.event.ChargedGiftPointHandler
import jp.glory.practice.eventStoreDb.user.infra.store.GiftPointHistoryDao
import jp.glory.practice.eventStoreDb.user.infra.store.GiftPointHistoryRecord
import jp.glory.practice.eventStoreDb.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class ChargedGiftPointHandlerImpl(
    private val dao: UserDao
) : ChargedGiftPointHandler {
    override fun handle(event: ChargedGiftPoint): Unit =
        dao.findById(event.userId.value)
            ?.let {
                it.copy(
                    giftPoint = it.giftPoint + event.chargeAmount
                )
            }
            ?.let { dao.save(it) }
            ?: throw IllegalStateException("User not found")

}