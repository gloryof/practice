package jp.glory.practice.axon.user.domain.event

import jp.glory.practice.axon.user.domain.model.UserId

class RecordGiftHistory private constructor(
    val userId: UserId,
    val type: Type,
    val amount: UInt
) {
    companion object {
        fun use(
            userId: UserId,
            amount: UInt
        ): RecordGiftHistory =
            RecordGiftHistory(
                userId = userId,
                type = Type.Use,
                amount = amount
            )

        fun charge(
            userId: UserId,
            amount: UInt
        ): RecordGiftHistory =
            RecordGiftHistory(
                userId = userId,
                type = Type.Charge,
                amount = amount
            )
    }

    enum class Type {
        Charge,
        Use
    }
}