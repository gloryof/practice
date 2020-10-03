package jp.glory.monitor.jvm.practice.app.goods.domain

interface GoodsSaveEventRepository {
    fun register(event: GoodsRegisterEvent): GoodsId
    fun update(event: GoodsUpdateEvent): GoodsId
}