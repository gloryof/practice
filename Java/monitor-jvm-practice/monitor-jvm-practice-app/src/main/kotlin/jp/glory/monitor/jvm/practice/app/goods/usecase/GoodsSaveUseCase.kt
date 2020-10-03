package jp.glory.monitor.jvm.practice.app.goods.usecase

import jp.glory.monitor.jvm.practice.app.base.Either
import jp.glory.monitor.jvm.practice.app.base.Left
import jp.glory.monitor.jvm.practice.app.base.Right
import jp.glory.monitor.jvm.practice.app.base.usecase.UseCase
import jp.glory.monitor.jvm.practice.app.base.usecase.UseCaseErrors
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsRegisterEventSpec
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsSaveEventRepository
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsUpdateEventSpec

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