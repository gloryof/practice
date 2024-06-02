package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.City
import jp.glory.practice.axon.user.domain.model.PostalCode
import jp.glory.practice.axon.user.domain.model.Prefecture
import jp.glory.practice.axon.user.domain.model.Street
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName

interface CreatedUserHandler {
    fun handle(event: CreatedUser)
}

class CreatedUser private constructor(
    val userId: UserId,
    val name: UserName,
    val address: Address
) {
    companion object {
        fun create(
            name: String,
            postalCode: String,
            prefectureCode: String,
            city: String,
            street: String
        ): CreatedUser {

            return CreatedUser(
                userId = UserId.generate(),
                name = UserName(name),
                address = Address(
                    postalCode = PostalCode(postalCode),
                    prefecture = Prefecture.fromCode(prefectureCode),
                    city = City(city),
                    street = Street(street)
                )
            )
        }
    }
}