package jp.glory.practice.axon.user.infra.axon.event

import jp.glory.practice.axon.user.domain.event.CreatedUser
import jp.glory.practice.axon.user.infra.store.UserDao
import jp.glory.practice.axon.user.infra.store.UserRecord
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class CreatedUserHandler(
    private val userDao: UserDao
)  {
    @EventHandler
    fun on(event: CreatedUser) {
        createRecord(event)
            .let { userDao.save(it) }
    }

    private fun createRecord(
        event: CreatedUser,
    ): UserRecord =
        UserRecord(
            userId = event.userId.value,
            userName = event.name.value,
            postalCode = event.address.postalCode.value,
            prefectureCode = event.address.prefecture.code,
            city = event.address.city.value,
            street = event.address.street.value,
            giftPoint = 0u
        )
}