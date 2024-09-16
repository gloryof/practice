package jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.CreatedUser
import jp.glory.practice.eventStoreDb.user.domain.event.CreatedUserHandler
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.EventStoreDbClient
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.CreatedUserData
import jp.glory.practice.eventStoreDb.user.infra.store.eventStoreDb.data.CreatedUserStoreDbData
import org.springframework.stereotype.Component

@Component
class CreatedUserHandlerImpl(
    private val client: EventStoreDbClient
) : CreatedUserHandler {
    override fun handle(event: CreatedUser) {
        toEventData(event)
            .also { client.append(CreatedUserStoreDbData(it)) }
    }

    private fun toEventData(event: CreatedUser): CreatedUserData =
        CreatedUserData(
            userId = event.userId.value,
            userName = event.name.value,
            postalCode = event.address.postalCode.value,
            prefectureCode = event.address.prefecture.code,
            city = event.address.city.value,
            street = event.address.street.value,
        )
}
