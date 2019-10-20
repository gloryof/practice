package jp.glory.neo4jstudy.usecase.annotation

import org.graalvm.compiler.serviceprovider.ServiceProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * ユースケースを表すアノテーション.
 */
@Service
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional
annotation class Usecase