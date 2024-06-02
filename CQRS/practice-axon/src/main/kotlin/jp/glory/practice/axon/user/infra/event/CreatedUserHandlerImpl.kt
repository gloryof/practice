package jp.glory.practice.axon.user.infra.event

import jp.glory.practice.axon.user.domain.event.CreatedUser
import jp.glory.practice.axon.user.domain.event.CreatedUserHandler
import jp.glory.practice.axon.user.infra.store.UserDao
import jp.glory.practice.axon.user.infra.store.UserRecord
import org.springframework.stereotype.Component

@Component
class CreatedUserHandlerImpl(
    private val userDao: UserDao
) : CreatedUserHandler {
    override fun handle(event: CreatedUser) {
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