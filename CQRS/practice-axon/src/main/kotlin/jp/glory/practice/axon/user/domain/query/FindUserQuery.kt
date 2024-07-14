package jp.glory.practice.axon.user.domain.query

import jp.glory.practice.axon.user.domain.command.ChangeAddressCommand
import jp.glory.practice.axon.user.domain.command.ChangeNameCommand
import jp.glory.practice.axon.user.domain.command.ChargeGiftPointCommand
import jp.glory.practice.axon.user.domain.command.UseGiftPointCommand
import jp.glory.practice.axon.user.domain.model.Address
import jp.glory.practice.axon.user.domain.model.GiftPoint
import jp.glory.practice.axon.user.domain.model.UserId
import jp.glory.practice.axon.user.domain.model.UserName

class FindUserQuery(
    val userId: UserId,
)

class FindUserResult(
    val userId: UserId,
    val name: UserName,
    val address: Address,
    val giftPoint: GiftPoint
) {

    fun toChangeAddressCommand(
        address: Address
    ): ChangeAddressCommand =
        ChangeAddressCommand(
            userId = userId,
            address = address
        )

    fun toChangeNameCommand(
        name: UserName
    ): ChangeNameCommand =
        ChangeNameCommand(
            userId = userId,
            name = name
        )

    fun toChargeGiftPointCommand(chargeAmount: UInt): ChargeGiftPointCommand =
        ChargeGiftPointCommand(
            userId = userId,
            chargeAmount = chargeAmount
        )

    fun toUseGiftPointCommand(useAmount: UInt): UseGiftPointCommand {
        if (!giftPoint.canUse(useAmount)) {
            throw IllegalArgumentException("Over remaining amount")
        }

        return UseGiftPointCommand(
            userId = userId,
            useAmount = useAmount
        )
    }
}