package jp.glory.practice.eventStoreDb

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class PracticeEventStoreDbApplication

fun main(args: Array<String>) {
	runApplication<PracticeEventStoreDbApplication>(*args)
}
