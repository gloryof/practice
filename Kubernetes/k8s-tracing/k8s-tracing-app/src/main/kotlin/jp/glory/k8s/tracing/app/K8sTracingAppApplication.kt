package jp.glory.k8s.tracing.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sTracingAppApplication

fun main(args: Array<String>) {
	runApplication<K8sTracingAppApplication>(*args)
}
