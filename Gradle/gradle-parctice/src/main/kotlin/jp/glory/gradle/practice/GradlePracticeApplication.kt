package jp.glory.gradle.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GradlePracticeApplication

fun main(args: Array<String>) {
	runApplication<GradlePracticeApplication>(*args)
}
