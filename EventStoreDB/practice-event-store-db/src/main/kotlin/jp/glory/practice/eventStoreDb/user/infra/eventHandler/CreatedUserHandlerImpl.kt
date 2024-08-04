package jp.glory.practice.eventStoreDb.user.infra.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.CreatedUser
import jp.glory.practice.eventStoreDb.user.domain.event.CreatedUserHandler
import jp.glory.practice.eventStoreDb.user.infra.store.UserDao
import jp.glory.practice.eventStoreDb.user.infra.store.UserRecord
import org.springframework.stereotype.Component

@Component
class CreatedUserHandlerImpl(
    private val dao: UserDao
) : CreatedUserHandler {
    override fun handle(event: CreatedUser) {
        toRecord(event)
            .also { dao.save(it) }
    }

    private fun toRecord(event: CreatedUser): UserRecord =
        UserRecord(
            userId = event.userId.value,
            userName = event.name.value,
            postalCode = event.address.postalCode.value,
            prefectureCode = event.address.prefecture.code,
            city = event.address.city.value,
            street = event.address.street.value,
            giftPoint = 0U
        )
}