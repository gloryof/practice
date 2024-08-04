package jp.glory.practice.eventStoreDb.user.infra.eventHandler

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddress
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddressHandler
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedName
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedNameHandler
import jp.glory.practice.eventStoreDb.user.infra.store.UserDao
import org.springframework.stereotype.Component

@Component
class ChangedNameHandlerImpl(
    private val dao: UserDao
) : ChangedNameHandler {
    override fun handle(event: ChangedName): Unit =
        dao.findById(event.userId.value)
            ?.let {
                it.copy(
                    userName = event.name.value
                )
            }
            ?.let { dao.save(it) }
            ?: throw IllegalStateException("User not found")
}