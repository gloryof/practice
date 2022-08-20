package jp.glory.pratice.test_container.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection

class RedisCacheClient(
    host: String,
    port: Int
) {
    private val connection: StatefulRedisConnection<String, String> =
        RedisClient.create("redis://$host:$port/0").connect()

    fun set(key: String, value: String) {
        connection.sync().set(key, value)
    }

    fun get(key: String): String? = connection.sync().get(key)
}