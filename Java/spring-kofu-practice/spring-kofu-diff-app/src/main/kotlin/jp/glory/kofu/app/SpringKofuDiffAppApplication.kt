package jp.glory.kofu.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKofuDiffAppApplication

fun main(args: Array<String>) {
	runApplication<SpringKofuDiffAppApplication>(*args)
}
