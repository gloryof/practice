package jp.glory.rethinkdb.practice.base.spring.conf

import com.rethinkdb.RethinkDB
import jp.glory.rethinkdb.practice.infra.lib.rethinkdb.RethinkDBConnector
import jp.glory.rethinkdb.practice.todo.domain.model.TodoIdGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class InjectionConfig {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()

    @Bean
    fun todoIdGenerator(): TodoIdGenerator = TodoIdGenerator()

    @Bean
    fun rethinkDBConnector(): RethinkDBConnector =
        RethinkDBConnector(
            rethinkDB = RethinkDB.r,
            hostName = "localhost",
            port = 30015,
            dbName = "todo"
        )

}