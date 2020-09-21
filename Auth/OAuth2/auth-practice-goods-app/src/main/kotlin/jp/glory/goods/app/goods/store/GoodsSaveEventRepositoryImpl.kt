package jp.glory.goods.app.goods.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.goods.app.goods.domain.GoodsId
import jp.glory.goods.app.goods.domain.GoodsRegisterEvent
import jp.glory.goods.app.goods.domain.GoodsSaveEventRepository
import jp.glory.goods.app.goods.domain.GoodsUpdateEvent
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class GoodsSaveEventRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : GoodsSaveEventRepository {
    override fun register(event: GoodsRegisterEvent): GoodsId {
        val goodsId = GoodsId(generateId())
        save(
            goodsId = goodsId,
            name = event.name,
            price = event.price
        )

        return goodsId
    }

    override fun update(event: GoodsUpdateEvent): GoodsId {
        save(
            goodsId = event.id,
            name = event.name,
            price = event.price
        )

        return event.id
    }
    private fun save(
        goodsId: GoodsId,
        name: String,
        price: Int
    ) {
        template.opsForValue().set(
            generateKey(goodsId),
            mapper.writeValueAsString(
                GoodsData(
                    id = goodsId.value,
                    name = name,
                    price = price
                )
            )
        )
    }

    private fun generateId(): String = UUID.randomUUID().toString()
}