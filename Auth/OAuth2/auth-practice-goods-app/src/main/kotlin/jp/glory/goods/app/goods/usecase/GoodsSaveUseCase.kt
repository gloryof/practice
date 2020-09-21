package jp.glory.goods.app.goods.usecase

import jp.glory.goods.app.base.Either
import jp.glory.goods.app.base.Left
import jp.glory.goods.app.base.Right
import jp.glory.goods.app.base.usecase.UseCase
import jp.glory.goods.app.base.usecase.UseCaseErrors
import jp.glory.goods.app.goods.domain.GoodsRegisterEventSpec
import jp.glory.goods.app.goods.domain.GoodsSaveEventRepository
import jp.glory.goods.app.goods.domain.GoodsUpdateEventSpec

@UseCase
class GoodsSaveUseCase (
    private val repository: GoodsSaveEventRepository
) {
    fun register(
        name: String?,
        price: String?
    ): Either<UseCaseErrors, String> {
        return GoodsRegisterEventSpec.validate(
            name = name,
            price = price
        ).mapBoth(
            right = { Right(repository.register(it).value) },
            left = { Left(UseCaseErrors(it)) }
        )
    }

    fun update(
        goodsId: String?,
        name: String?,
        price: String?
    ): Either<UseCaseErrors, String> {
        return GoodsUpdateEventSpec.validate(
            goodsId = goodsId,
            name = name,
            price = price
        ).mapBoth(
            right = { Right(repository.update(it).value) },
            left = { Left(UseCaseErrors(it)) }
        )
    }
}