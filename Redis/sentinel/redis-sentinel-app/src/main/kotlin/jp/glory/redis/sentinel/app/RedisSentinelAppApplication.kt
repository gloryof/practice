package jp.glory.redis.sentinel.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedisSentinelAppApplication

fun main(args: Array<String>) {
	runApplication<RedisSentinelAppApplication>(*args)
}
