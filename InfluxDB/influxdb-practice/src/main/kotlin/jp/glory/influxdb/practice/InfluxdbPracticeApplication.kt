package jp.glory.influxdb.practice

import jp.glory.influxdb.practice.base.spring.config.InfluxDBProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
	InfluxDBProperty::class
)
class InfluxdbPracticeApplication

fun main(args: Array<String>) {
	runApplication<InfluxdbPracticeApplication>(*args)
}
