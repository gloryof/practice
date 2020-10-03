package jp.glory.monitor.jvm.practice.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MonitorJvmPracticeAppApplication

fun main(args: Array<String>) {
	runApplication<MonitorJvmPracticeAppApplication>(*args)
}
