package jp.glory.neo4jstudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Neo4jStudyApplication

fun main(args: Array<String>) {
	runApplication<Neo4jStudyApplication>(*args)
}
