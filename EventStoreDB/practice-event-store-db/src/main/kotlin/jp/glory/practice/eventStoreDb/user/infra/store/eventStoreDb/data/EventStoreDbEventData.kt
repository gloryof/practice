package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data

import com.eventstore.dbclient.EventData
import com.fasterxml.jackson.databind.json.JsonMapper

abstract class EventStoreDbEventData<T>(
    private val data: T
) {
    fun toEventData(
        mapper: JsonMapper
    ): EventData =
        EventData.builderAsJson(getTypeName(), data)
            .build()

    abstract fun getType(): EventDataType

    private fun getTypeName(): String = getType().value

    fun getStreamName(): String = getType().getStreamName()
}

enum class EventDataType(val value: String) {
    CreatedUser("created-user"),
    ChangedName("changed-name"),
    ChangedAddress("changed-address"),
    ChargedGiftPoint("charged-gift-point"),
    UsedGiftPoint("used-gift-point"),
    RecordedGiftHistory("used-gift-history");

    fun getStreamName(): String = "$value-stream"
}