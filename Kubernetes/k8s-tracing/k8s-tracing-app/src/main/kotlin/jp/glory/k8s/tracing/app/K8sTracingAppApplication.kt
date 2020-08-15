package jp.glory.k8s.tracing.app

import ch.qos.logback.access.tomcat.LogbackValve
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean


@SpringBootApplication
class K8sTracingAppApplication

fun main(args: Array<String>) {
	runApplication<K8sTracingAppApplication>(*args)
}
