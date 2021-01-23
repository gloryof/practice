package jp.glory.practice.bench.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PracticeBenchAppApplication

fun main(args: Array<String>) {
	runApplication<PracticeBenchAppApplication>(*args)
}
