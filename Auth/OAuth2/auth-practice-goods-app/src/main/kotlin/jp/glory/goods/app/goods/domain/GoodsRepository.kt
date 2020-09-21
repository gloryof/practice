package jp.glory.goods.app.goods.domain

interface GoodsRepository {
    fun findAll(): List<Goods>
    fun findById(goodsId: GoodsId): Goods?
}