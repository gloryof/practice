package jp.glory.practice.eventStoreDb.user.usecase.command

import jp.glory.practice.eventStoreDb.user.domain.event.CreatedUser
import jp.glory.practice.eventStoreDb.user.domain.event.CreatedUserHandler
import jp.glory.practice.eventStoreDb.user.domain.model.Address
import jp.glory.practice.eventStoreDb.user.domain.model.City
import jp.glory.practice.eventStoreDb.user.domain.model.PostalCode
import jp.glory.practice.eventStoreDb.user.domain.model.Prefecture
import jp.glory.practice.eventStoreDb.user.domain.model.Street
import jp.glory.practice.eventStoreDb.user.domain.model.UserId
import jp.glory.practice.eventStoreDb.user.domain.model.UserName
import org.springframework.stereotype.Service


@Service
class CreateUserUseCase(
    private val eventHandler: CreatedUserHandler
) {
    fun create(input: Input): Output =
        input.toEvent()
            .also { eventHandler.handle(it) }
            .let { Output(it.userId.value) }

    class Input(
        val name: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toEvent(): CreatedUser =
            CreatedUser.create(
                userId = UserId.generate(),
                name = UserName(name),
                address = Address(
                    postalCode = PostalCode(postalCode),
                    prefecture = Prefecture.fromCode(prefectureCode),
                    city = City(city),
                    street = Street(street)
                ),
            )
    }

    class Output(
        val userId: String
    )
}