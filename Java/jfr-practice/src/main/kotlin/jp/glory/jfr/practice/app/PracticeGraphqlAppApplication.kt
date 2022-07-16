package jp.glory.jfr.practice.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class JfrPracticeAppApplication

fun main(args: Array<String>) {
    runApplication<JfrPracticeAppApplication>(*args)
}
