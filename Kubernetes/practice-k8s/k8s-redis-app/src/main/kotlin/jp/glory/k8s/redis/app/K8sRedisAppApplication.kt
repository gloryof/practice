package jp.glory.k8s.redis.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sRedisAppApplication

fun main(args: Array<String>) {
	runApplication<K8sRedisAppApplication>(*args)
}