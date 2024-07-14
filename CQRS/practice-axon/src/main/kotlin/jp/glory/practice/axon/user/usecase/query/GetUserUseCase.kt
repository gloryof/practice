package jp.glory.practice.axon.user.usecase.query

import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.query.FindUserQuery
import jp.glory.practice.axon.user.domain.query.FindUserResult
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.springframework.stereotype.Service
import jp.glory.practice.axon.user.domain.model.Address as DomainAddress

@Service
class GetUserUseCase(
    private val userRepository: UserRepository,
    private val queryBus: QueryBus
) {
    fun findById(input: Input): Output =
        queryBus.query(toQuery(input.userId))
            .get()
            .let { it.payload }
            ?.let { Output.create(it) }
            ?: throw IllegalArgumentException("User not found")

    private fun toQuery(userId: String): GenericQueryMessage<FindUserQuery, FindUserResult> =
        GenericQueryMessage(
            FindUserQuery(UserId.fromString(userId)),
            ResponseTypes.instanceOf(FindUserResult::class.java)
        )


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
            fun create(result: FindUserResult): Output =
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