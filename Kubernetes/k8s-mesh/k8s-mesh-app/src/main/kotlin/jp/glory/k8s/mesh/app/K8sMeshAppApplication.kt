package jp.glory.k8s.mesh.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sMeshAppApplication

fun main(args: Array<String>) {
	runApplication<K8sMeshAppApplication>(*args)
}
