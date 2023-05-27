package jp.glory.kafka.practice.base.spring.config

import jp.glory.kafka.practice.todo.domain.model.TodoIdGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class InjectionConfig {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()

    @Bean
    fun todoIdGenerator(): TodoIdGenerator = TodoIdGenerator()
}