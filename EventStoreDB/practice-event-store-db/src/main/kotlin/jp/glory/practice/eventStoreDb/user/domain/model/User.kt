package jp.glory.practice.eventStoreDb.user.domain.model

import jp.glory.practice.eventStoreDb.user.domain.event.ChangedAddress
import jp.glory.practice.eventStoreDb.user.domain.event.ChangedName
import jp.glory.practice.eventStoreDb.user.domain.event.ChargedGiftPoint
import jp.glory.practice.eventStoreDb.user.domain.event.UsedGiftPoint
import java.util.UUID

class User(
    val userId: UserId,
    val name: UserName,
    val address: Address,
    val giftPoint: GiftPoint
) {
    fun changeName(newName: UserName): ChangedName =
        ChangedName(
            userId = userId,
            name = newName
        )

    fun changeAddress(newAddress: Address): ChangedAddress =
        ChangedAddress(
            userId = userId,
            address = newAddress
        )

    fun chargeGiftPoint(chargeAmount: UInt): ChargedGiftPoint =
        ChargedGiftPoint.create(
            userId = userId,
            chargeAmount = chargeAmount,
        )

    fun useGiftPoint(useAmount: UInt): UsedGiftPoint =
        UsedGiftPoint.create(
            userId = userId,
            useAmount = useAmount,
        )
}

@JvmInline
value class UserId private constructor(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 100)
    }

    companion object {
        fun generate(): UserId =
            UUID.randomUUID().toString()
                .let { UserId(it) }

        fun fromString(value: String) = UserId(value)
    }
}

@JvmInline
value class UserName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 100)
    }
}

@JvmInline
value class GiftPoint(
    val value: UInt
) {
    fun use(amount: UInt): GiftPoint {
        if (!canUse(amount)) {
            throw IllegalArgumentException("Over remaining amount")
        }

        return GiftPoint(value - amount)
    }

    fun charge(amount: UInt): GiftPoint =
        GiftPoint(value + amount)

    fun canUse(amount: UInt): Boolean =
        value >= amount

}