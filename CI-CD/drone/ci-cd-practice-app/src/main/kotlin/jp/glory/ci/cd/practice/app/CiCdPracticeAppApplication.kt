package jp.glory.ci.cd.practice.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CiCdPracticeAppApplication

fun main(args: Array<String>) {
	runApplication<CiCdPracticeAppApplication>(*args)
}
