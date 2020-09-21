package jp.glory.goods.app.goods.usecase

import jp.glory.goods.app.base.usecase.UseCase
import jp.glory.goods.app.goods.domain.Goods
import jp.glory.goods.app.goods.domain.GoodsId
import jp.glory.goods.app.goods.domain.GoodsRepository

@UseCase
class GoodsSearchUseCase(
    private val repository: GoodsRepository
) {
    fun findAll(): GoodsSearchResult = repository
        .findAll()
        .map { GoodsDetail(it) }
        .let { GoodsSearchResult(it) }

    fun findById(id: String): GoodsDetail? = repository
        .findById(GoodsId(id))
        ?.let { GoodsDetail(it) }
}

class GoodsSearchResult(
    val details: List<GoodsDetail>
)

class GoodsDetail(
    val goodsId: String,
    val name: String,
    val price: Int
) {
    constructor(goods: Goods) : this(
        goodsId = goods.id.value,
        name = goods.name,
        price = goods.price
    )
}