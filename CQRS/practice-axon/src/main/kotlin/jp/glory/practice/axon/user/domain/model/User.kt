package jp.glory.practice.axon.user.domain.model

import jp.glory.practice.axon.user.domain.command.ChangeAddressCommand
import jp.glory.practice.axon.user.domain.command.ChangeNameCommand
import jp.glory.practice.axon.user.domain.command.ChargeGiftPointCommand
import jp.glory.practice.axon.user.domain.command.UseGiftPointCommand
import java.util.UUID

class User(
    val userId: UserId,
    val name: UserName,
    val address: Address,
    val giftPoint: GiftPoint
) {
    fun changeName(newName: UserName): User =
        change(
            newName = newName
        )

    fun changeAddress(newAddress: Address): User =
        change(
            newAddress = newAddress
        )

    fun chargeGiftPoint(chargeAmount: UInt): User =
        change(
            newGiftPoint = giftPoint.charge(chargeAmount)
        )

    fun useGiftPoint(chargeAmount: UInt): User =
        change(
            newGiftPoint = giftPoint.use(chargeAmount)
        )

    fun executeChangeName(newName: UserName): ChangeNameCommand =
        ChangeNameCommand(
            userId = userId,
            name = newName
        )


    fun executeChangeAddress(newAddress: Address): ChangeAddressCommand =
        ChangeAddressCommand(
            userId = userId,
            address = newAddress
        )

    fun executeChargeGiftPoint(chargeAmount: UInt): ChargeGiftPointCommand =
        ChargeGiftPointCommand(
            userId = userId,
            chargeAmount = chargeAmount
        )

    fun executeUseGiftPoint(useAmount: UInt): UseGiftPointCommand {
        if (!giftPoint.canUse(useAmount)) {
            throw IllegalArgumentException("Over remaining amount")
        }

        return UseGiftPointCommand(
            userId = userId,
            useAmount = useAmount
        )
    }

    private fun change(
        newName: UserName? = null,
        newAddress: Address? = null,
        newGiftPoint: GiftPoint? = null
    ): User =
        User(
            userId = this.userId,
            name = newName ?: this.name,
            address = newAddress?: this.address,
            giftPoint = newGiftPoint ?: this.giftPoint
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