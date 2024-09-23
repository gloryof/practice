package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.listener

import com.eventstore.dbclient.ResolvedEvent
import com.eventstore.dbclient.Subscription
import com.eventstore.dbclient.SubscriptionListener
import com.fasterxml.jackson.module.kotlin.readValue
import jp.glory.practice.eventStoreDb.user.infra.store.dao.UserDao
import jp.glory.practice.eventStoreDb.user.infra.store.dao.UserRecord
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChangedNameData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.CreatedUserData
import org.springframework.stereotype.Component

@Component
class ChangedNameDataListener(
    private val client: EventStoreDbClient,
    private val dao: UserDao
) : SubscriptionListener() {
    override fun onEvent(subscription: Subscription, resolvedEvent: ResolvedEvent) {
        val event = resolvedEvent.originalEvent.eventData
        val parsed: ChangedNameData = client.mapper.readValue(event)

        dao.findById(parsed.userId)
            ?.let { it.copy(userName = parsed.userName) }
            ?.also { dao.save(it) }
    }
}