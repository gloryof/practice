package jp.glory.rethinkdb.practice

import jp.glory.rethinkdb.practice.base.spring.conf.listener.ApplicationStartedEventListener
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RethinkdbPracticeApplication

fun main(args: Array<String>) {
	val app = SpringApplication(RethinkdbPracticeApplication::class.java)
	app.addListeners(ApplicationStartedEventListener())
	app.run(*args)
}
