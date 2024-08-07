package jp.glory.practice.eventStoreDb.user.usecase.command

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddressHandler
import jp.glory.practice.eventStoreDb.user.domain.model.Address
import jp.glory.practice.eventStoreDb.user.domain.model.City
import jp.glory.practice.eventStoreDb.user.domain.model.PostalCode
import jp.glory.practice.eventStoreDb.user.domain.model.Prefecture
import jp.glory.practice.eventStoreDb.user.domain.model.Street
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ChangeAddressUseCase(
    private val handler: ChangedAddressHandler,
    private val repository: UserRepository
) {
    fun change(input: Input) {
        repository.findById(UserId.fromString(input.userId))
            ?.also { handler.handle(it.changeAddress(input.toAddress())) }
            ?: throw IllegalArgumentException("User not found")
    }

    class Input(
        val userId: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toAddress(): Address =
            Address(
                postalCode = PostalCode(postalCode),
                prefecture = Prefecture.fromCode(prefectureCode),
                city = City(city),
                street = Street(street)
            )
    }
}