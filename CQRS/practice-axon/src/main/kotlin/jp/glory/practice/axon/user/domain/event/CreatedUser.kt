package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName

class CreatedUser private constructor(
    val userId: UserId,
    val name: UserName,
    val address: Address
) {
    companion object {
        fun create(
            userId: UserId,
            name: UserName,
            address: Address
        ): CreatedUser {

            return CreatedUser(
                userId = userId,
                name = name,
                address = address
            )
        }
    }
}