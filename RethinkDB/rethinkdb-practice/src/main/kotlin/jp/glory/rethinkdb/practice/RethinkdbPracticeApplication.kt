package jp.glory.rethinkdb.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RethinkdbPracticeApplication

fun main(args: Array<String>) {
	runApplication<RethinkdbPracticeApplication>(*args)
}
