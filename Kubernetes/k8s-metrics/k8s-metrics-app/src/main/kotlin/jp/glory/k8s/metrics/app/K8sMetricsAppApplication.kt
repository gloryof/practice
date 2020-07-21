package jp.glory.k8s.metrics.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sMetricsAppApplication

fun main(args: Array<String>) {
	runApplication<K8sMetricsAppApplication>(*args)
}
