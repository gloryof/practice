package jp.glory.goods.app.goods.store

import jp.glory.goods.app.goods.domain.GoodsId

data class GoodsData(
    val id: String,
    val name: String,
    val price: Int
)

fun keyTemplate(value: String) = "goods:${value}"
fun generateKey(id: GoodsId): String = keyTemplate(id.value)