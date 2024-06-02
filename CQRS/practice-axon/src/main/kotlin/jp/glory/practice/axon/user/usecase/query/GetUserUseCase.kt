package jp.glory.practice.axon.user.usecase.query

import jp.glory.practice.axon.user.domain.model.User
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import jp.glory.practice.axon.user.domain.model.Address as DomainAddress

@Service
class GetUserUseCase(
    private val userRepository: UserRepository
) {
    fun findById(input: Input): Output =
        userRepository.findById(UserId.fromString(input.userId))
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
            fun create(user: User): Output =
                Output(
                    userId = user.userId.value,
                    name = user.name.value,
                    address = Address.create(user.address),
                    remainingGiftPoint = user.giftPoint.value
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