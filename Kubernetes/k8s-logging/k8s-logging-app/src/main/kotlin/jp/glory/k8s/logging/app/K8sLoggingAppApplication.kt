package jp.glory.k8s.logging.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sLoggingAppApplication

fun main(args: Array<String>) {
	runApplication<K8sLoggingAppApplication>(*args)
}
