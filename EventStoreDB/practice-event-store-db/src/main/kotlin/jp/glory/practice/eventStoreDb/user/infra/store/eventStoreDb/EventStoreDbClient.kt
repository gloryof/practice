package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb

import com.eventstore.dbclient.EventStoreDBClientSettings
import com.eventstore.dbclient.SubscriptionListener
import com.fasterxml.jackson.core.StreamReadFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.EventDataType
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.EventStoreDbEventData
import org.springframework.stereotype.Component
import com.eventstore.dbclient.EventStoreDBClient as LibClient

@Component
class EventStoreDbClient {
    private val client = EventStoreDBClientSettings
        .builder()
        .addHost("localhost", 30113)
        .tls(false)
        .buildConnectionSettings()
        .let { LibClient.create(it) }

    val mapper = jsonMapper {
        enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
        addModule(kotlinModule())
        addModule(JavaTimeModule())
    }

    fun <T> append(
        data: EventStoreDbEventData<T>
    ) {
        data.toEventData(mapper)
            .let { client.appendToStream(data.getStreamName(), it) }
            .let { it.get() }
    }

    fun subscribe(
        type: EventDataType,
        listener: SubscriptionListener
    ) {
        client.subscribeToStream(type.getStreamName(), listener)
    }
}

