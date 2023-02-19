package jp.glory.influxdb.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InfluxdbPracticeApplication

fun main(args: Array<String>) {
	runApplication<InfluxdbPracticeApplication>(*args)
}
