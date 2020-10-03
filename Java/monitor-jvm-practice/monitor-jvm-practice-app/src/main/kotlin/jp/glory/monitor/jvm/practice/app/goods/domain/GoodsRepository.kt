package jp.glory.monitor.jvm.practice.app.goods.domain

interface GoodsRepository {
    fun findAll(): List<Goods>
    fun findById(goodsId: GoodsId): Goods?
}