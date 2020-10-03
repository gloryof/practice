package jp.glory.monitor.jvm.practice.app.goods.store

import jp.glory.monitor.jvm.practice.app.goods.domain.Goods
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsId
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsRegisterEvent
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsRepository
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsSaveEventRepository
import jp.glory.monitor.jvm.practice.app.goods.domain.GoodsUpdateEvent
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class GoodsRepositoryImpl : GoodsRepository, GoodsSaveEventRepository {
    companion object {
        private val dataMap: MutableMap<String, Goods> = mutableMapOf()
    }

    override fun findAll(): List<Goods> = dataMap.values.toList()

    override fun findById(goodsId: GoodsId): Goods? = dataMap[goodsId.value]

    override fun register(event: GoodsRegisterEvent): GoodsId =
        generateId()
            .also {
                dataMap[it.value] = Goods(
                    id = it,
                    name = event.name,
                    price = event.price
                )
            }

    override fun update(event: GoodsUpdateEvent): GoodsId {
        val id = event.id

        dataMap[id.value]
            ?.let {
                dataMap[id.value] = it.copy(
                    name = event.name,
                    price = event.price
                )
            }

        return id
    }

    private fun generateId(): GoodsId = GoodsId(UUID.randomUUID().toString())
}