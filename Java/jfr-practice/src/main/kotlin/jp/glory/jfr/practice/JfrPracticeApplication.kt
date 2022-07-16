package jp.glory.jfr.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JfrPracticeApplication

fun main(args: Array<String>) {
	runApplication<JfrPracticeApplication>(*args)
}
