package jp.glory.practicegraphql.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class PracticeGraphqlAppApplication

fun main(args: Array<String>) {
    runApplication<PracticeGraphqlAppApplication>(*args)
}
