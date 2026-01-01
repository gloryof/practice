package jp.glory.practice.boot.app

import jp.glory.practice.boot.app.base.spring.ApplicationImportConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ApplicationImportConfig::class)
class SpringBootAppApplication

fun main(args: Array<String>) {
    runApplication<SpringBootAppApplication>(*args)
}
