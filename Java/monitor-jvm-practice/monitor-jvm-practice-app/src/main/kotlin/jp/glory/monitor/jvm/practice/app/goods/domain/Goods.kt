package jp.glory.monitor.jvm.practice.app.goods.domain

data class Goods(
    val id: GoodsId,
    val name: String,
    val price: Int
)

data class GoodsId(val value: String)