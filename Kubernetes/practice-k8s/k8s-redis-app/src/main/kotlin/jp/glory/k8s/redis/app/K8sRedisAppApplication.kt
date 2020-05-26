package jp.glory.k8s.redis.app

import ch.qos.logback.access.tomcat.LogbackValve
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@SpringBootApplication
class K8sRedisAppApplication

fun main(args: Array<String>) {
	runApplication<K8sRedisAppApplication>(*args)
}

@Configuration
class TomcatLoggingConfig {
	@Bean
	fun servletContainer(): TomcatServletWebServerFactory {
		val tomcatServletWebServerFactory = TomcatServletWebServerFactory()
		tomcatServletWebServerFactory.addContextValves(LogbackValve())
		return tomcatServletWebServerFactory
	}
}