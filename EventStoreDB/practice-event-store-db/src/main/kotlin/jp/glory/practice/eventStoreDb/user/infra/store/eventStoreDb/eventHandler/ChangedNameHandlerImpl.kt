package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedName
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedNameHandler
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChangedNameData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.ChangedNameStoreDbData
import org.springframework.stereotype.Component

@Component
class ChangedNameHandlerImpl(
    private val client: EventStoreDbClient
) : ChangedNameHandler {
    override fun handle(event: ChangedName): Unit =
        toEventData(event)
            .let { client.append(ChangedNameStoreDbData(it)) }

    private fun toEventData(event: ChangedName): ChangedNameData =
        ChangedNameData(
            userId = event.userId.value,
            userName = event.name.value
        )
}
