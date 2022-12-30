package jp.glory.boot3practice

import jp.glory.boot3practice.base.spring.observability.ObservabilityConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Boot3PracticeApplication {

	@Bean
	fun doOnStart(
		observabilityConfig: ObservabilityConfig
	): ApplicationListener<ApplicationStartedEvent> =
		ApplicationListener {
			observabilityConfig.configure()
		}
}

fun main(args: Array<String>) {
	runApplication<Boot3PracticeApplication>(*args)
}
