package jp.glory.neo4jstudy

import org.neo4j.ogm.session.SessionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager
import org.neo4j.ogm.config.ClasspathConfigurationSource
import org.neo4j.ogm.config.Configuration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories


@SpringBootApplication
class Neo4jStudyApplication

fun main(args: Array<String>) {
	runApplication<Neo4jStudyApplication>(*args)
}

