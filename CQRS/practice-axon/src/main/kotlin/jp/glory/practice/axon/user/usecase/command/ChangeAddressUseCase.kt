package jp.glory.practice.axon.user.usecase.command

import jp.glory.practice.axon.user.domain.command.ChangeAddressCommand
import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.City
import jp.glory.practice.axon.user.domain.model.PostalCode
import jp.glory.practice.axon.user.domain.model.Prefecture
import jp.glory.practice.axon.user.domain.model.Street
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class ChangeAddressUseCase(
    private val userRepository: UserRepository,
    private val commandGateway: CommandGateway
) {
    fun change(input: Input) =
        userRepository.findById(UserId.fromString(input.userId))
            ?.let { it.executeChangeAddress(input.toAddress()) }
            ?.let { commandGateway.send<ChangeAddressCommand>(it) }
            ?: throw IllegalArgumentException("Use not found")

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