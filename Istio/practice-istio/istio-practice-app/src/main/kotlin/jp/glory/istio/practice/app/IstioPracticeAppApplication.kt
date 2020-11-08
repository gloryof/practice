package jp.glory.istio.practice.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IstioPracticeAppApplication

fun main(args: Array<String>) {
	runApplication<IstioPracticeAppApplication>(*args)
}
