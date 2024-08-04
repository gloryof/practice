package jp.glory.practice.eventStoreDb.user.usecase.query

import jp.glory.practice.eventStoreDb.user.domain.model.User
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import jp.glory.practice.eventStoreDb.user.domain.model.Address as DomainAddress

@Service
class GetUserUseCase(
    private val repository: UserRepository
) {
    fun findById(input: Input): Output =
        repository.findById(UserId.fromString(input.userId))
            ?.let { Output.create(it) }
            ?: throw IllegalArgumentException("User not found")

    class Input(
        val userId: String
    )

    class Output private constructor(
        val userId: String,
        val name: String,
        val address: Address,
        val remainingGiftPoint: UInt
    ) {
        companion object {
            fun create(result: User): Output =
                Output(
                    userId = result.userId.value,
                    name = result.name.value,
                    address = Address.create(result.address),
                    remainingGiftPoint = result.giftPoint.value
                )
        }

        class Address private constructor(
            val postalCode: String,
            val prefectureName: String,
            val city: String,
            val street: String
        ) {
            companion object {
                fun create(address: DomainAddress): Address =
                    Address(
                        postalCode = address.postalCode.value,
                        prefectureName = address.prefecture.name,
                        city = address.city.value,
                        street = address.street.value
                    )
            }
        }
    }
}