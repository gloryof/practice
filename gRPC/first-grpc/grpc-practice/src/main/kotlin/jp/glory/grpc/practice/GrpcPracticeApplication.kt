package jp.glory.grpc.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcPracticeApplication

fun main(args: Array<String>) {
	runApplication<GrpcPracticeApplication>(*args)
}
