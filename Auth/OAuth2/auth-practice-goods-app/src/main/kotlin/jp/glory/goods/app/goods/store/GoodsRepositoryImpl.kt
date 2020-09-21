package jp.glory.goods.app.goods.store

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.goods.app.goods.domain.Goods
import jp.glory.goods.app.goods.domain.GoodsId
import jp.glory.goods.app.goods.domain.GoodsRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.lang.IllegalStateException

@Repository
class GoodsRepositoryImpl(
    private val template: StringRedisTemplate,
    private val mapper: ObjectMapper
) : GoodsRepository {
    override fun findAll(): List<Goods> =
        template.keys(keyTemplate("*"))
            .map { key ->
                template.opsForValue()
                    .get(key)
                    .let { it ?: throw IllegalStateException("Data must not be null") }
                    .let { mapper.readValue(it, GoodsData::class.java) }
                    .let {
                        Goods(
                            id = GoodsId(it.id),
                            name = it.name,
                            price = it.price
                        )
                    }
            }
            .sortedBy { it.name }

    override fun findById(goodsId: GoodsId): Goods? =
        template.opsForValue()
            .get(generateKey(goodsId))
            ?.let { mapper.readValue(it, GoodsData::class.java) }
            ?.let {
                Goods(
                    id = GoodsId(it.id),
                    name = it.name,
                    price = it.price
                )
            }
}