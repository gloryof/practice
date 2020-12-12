package jp.glory.k8s.hpa.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sHpaAppApplication

fun main(args: Array<String>) {
	runApplication<K8sHpaAppApplication>(*args)
}
