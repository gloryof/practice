package jp.glory.monitor.jvm.practice.app.goods.domain

import jp.glory.monitor.jvm.practice.app.base.Either
import jp.glory.monitor.jvm.practice.app.base.Right
import jp.glory.monitor.jvm.practice.app.base.domain.IntSpecValidator
import jp.glory.monitor.jvm.practice.app.base.domain.SpecErrorDetail
import jp.glory.monitor.jvm.practice.app.base.domain.SpecErrors
import jp.glory.monitor.jvm.practice.app.base.domain.StringSpecValidator

data class GoodsRegisterEvent(
    val name: String,
    val price: Int
)

data class GoodsUpdateEvent(
    val id: GoodsId,
    val name: String,
    val price: Int
)

object GoodsRegisterEventSpec {
    fun validate(
        name: String?,
        price: String?
    ): Either<SpecErrors, GoodsRegisterEvent> {
        val errors = SpecErrors()
        val result = MaybeGoodsRegisterEvent()

        errors.validateWith(
            validator = { GoodsSaveEventSpec.validateName(name) },
            right = { result.name = it }
        )
        errors.validateWith(
            validator = { GoodsSaveEventSpec.validatePrice(price) },
            right = { result.price = it }
        )

        return Right(result.toEvent())
    }

}
object GoodsUpdateEventSpec {
    fun validate(
        goodsId: String?,
        name: String?,
        price: String?
    ): Either<SpecErrors, GoodsUpdateEvent> {
        val errors = SpecErrors()
        val result = MaybeGoodsUpdateEvent()

        errors.validateWith(
            validator = { validateGoodsId(goodsId) },
            right = { result.id = GoodsId(it) }
        )
        errors.validateWith(
            validator = { GoodsSaveEventSpec.validateName(name) },
            right = { result.name = it }
        )
        errors.validateWith(
            validator = { GoodsSaveEventSpec.validatePrice(price) },
            right = { result.price = it }
        )

        return Right(result.toEvent())
    }

    private fun validateGoodsId(
        value: String?
    ): Either<SpecErrorDetail, String> {
        val validator = StringSpecValidator(
            name = "GoodsId",
            value = value
        )

        validator.required()

        return validator.toEither()
    }
}

private class MaybeGoodsRegisterEvent(
    var name: String? = null,
    var price: Int? = null
) {
    fun toEvent(): GoodsRegisterEvent =
        GoodsRegisterEvent(
            name = name ?: throw IllegalStateException("name is null"),
            price = price ?: throw IllegalStateException("price is null")
        )
}

private class MaybeGoodsUpdateEvent(
    var id: GoodsId? = null,
    var name: String? = null,
    var price: Int? = null
) {
    fun toEvent(): GoodsUpdateEvent =
        GoodsUpdateEvent(
            id = id ?: throw IllegalStateException("id is null"),
            name = name ?: throw IllegalStateException("name is null"),
            price = price ?: throw IllegalStateException("price is null")
        )
}

private object GoodsSaveEventSpec {
    fun validateName(
        value: String?
    ): Either<SpecErrorDetail, String> {
        val validator = StringSpecValidator(
            name = "name",
            value = value
        )

        validator.required()
        validator.whenNoError {
            validator.maxLength(20)
        }

        return validator.toEither()
    }

    fun validatePrice(
        value: String?
    ): Either<SpecErrorDetail, Int> {
        val validator = IntSpecValidator(
            name = "price",
            value = value
        )

        validator.required()
        validator.whenNoError {
            validator.numeric()
        }
        validator.whenNoError {
            validator.positive()
        }

        return validator.toEither()
    }
}