package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.listener

import com.eventstore.dbclient.ResolvedEvent
import com.eventstore.dbclient.Subscription
import com.eventstore.dbclient.SubscriptionListener
import com.fasterxml.jackson.module.kotlin.readValue
import jp.glory.practice.eventStoreDb.user.infra.store.dao.GiftPointHistoryDao
import jp.glory.practice.eventStoreDb.user.infra.store.dao.GiftPointHistoryRecord
import jp.glory.practice.eventStoreDb.user.infra.store.dao.UserDao
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChargedGiftData
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class ChargedGiftPointDataListener(
    private val client: EventStoreDbClient,
    private val userDao: UserDao,
) : SubscriptionListener() {
    override fun onEvent(subscription: Subscription, resolvedEvent: ResolvedEvent) {
        val event = resolvedEvent.originalEvent.eventData
        val parsed: ChargedGiftData = client.mapper.readValue(event)
        updateUser(parsed)
    }

    private fun updateUser(
        data: ChargedGiftData
    ) {
        userDao.findById(data.userId)
            ?.let { it.copy(giftPoint = it.giftPoint + data.chargeAmount) }
            ?.let { userDao.save(it) }
    }
}