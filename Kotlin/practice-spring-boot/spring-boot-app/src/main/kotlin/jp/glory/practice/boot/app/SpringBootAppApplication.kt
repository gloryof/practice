package jp.glory.practice.boot.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootAppApplication

fun main(args: Array<String>) {
	runApplication<SpringBootAppApplication>(*args)
}
