package jp.glory.k8s.probe.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sProbeAppApplication

fun main(args: Array<String>) {
	runApplication<K8sProbeAppApplication>(*args)
}
