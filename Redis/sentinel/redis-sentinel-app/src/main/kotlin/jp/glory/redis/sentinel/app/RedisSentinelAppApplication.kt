package jp.glory.redis.sentinel.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisSentinelConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory


@SpringBootApplication
class RedisSentinelAppApplication

fun main(args: Array<String>) {
	runApplication<RedisSentinelAppApplication>(*args)
}

@Bean
fun redisConnectionFactory(
	redisProperties: RedisProperties
): LettuceConnectionFactory? {
	val sentinelConfig: RedisSentinelConfiguration = RedisSentinelConfiguration()
		.master(redisProperties.sentinel.master)
	redisProperties.sentinel.nodes
		.forEach { s -> sentinelConfig.sentinel(s, redisProperties.port.toInt()) }
	sentinelConfig.password = RedisPassword.of(redisProperties.password)
	return LettuceConnectionFactory(sentinelConfig)
}
