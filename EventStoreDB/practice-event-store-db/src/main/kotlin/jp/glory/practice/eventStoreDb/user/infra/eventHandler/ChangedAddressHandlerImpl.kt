package jp.glory.practice.eventStoreDb.user.infra.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddress
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddressHandler
import jp.glory.practice.eventStoreDb.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class ChangedAddressHandlerImpl(
    private val dao: UserDao
) : ChangedAddressHandler {
    override fun handle(event: ChangedAddress): Unit =
        dao.findById(event.userId.value)
            ?.let {
                it.copy(
                    postalCode = event.address.postalCode.value,
                    prefectureCode = event.address.prefecture.code,
                    city = event.address.city.value,
                    street = event.address.street.value
                )
            }
            ?.let { dao.save(it) }
            ?: throw IllegalStateException("User not found")
}