package jp.glory.practice.axon.user.infra.axon.event

import jp.glory.practice.axon.user.domain.event.ChangedAddress
import jp.glory.practice.axon.user.infra.store.UserDao
import jp.glory.practice.axon.user.infra.store.UserRecord
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class ChangedAddressHandlerImpl(
    private val userDao: UserDao
) {
    @EventHandler
    fun on(event: ChangedAddress) {
        userDao.findById(event.userId.value)
            ?.let { update(event, it) }
            ?.let { userDao.save(it) }
            ?: throw IllegalArgumentException("Not found user")
    }

    private fun update(
        event: ChangedAddress,
        userRecord: UserRecord
    ): UserRecord {
        val address = event.address
        return userRecord.copy(
            postalCode = address.postalCode.value,
            prefectureCode = address.prefecture.code,
            city = address.city.value,
            street = address.street.value
        )
    }

}