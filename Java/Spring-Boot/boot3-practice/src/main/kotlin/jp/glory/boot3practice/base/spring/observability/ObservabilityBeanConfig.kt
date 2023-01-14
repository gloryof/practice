package jp.glory.boot3practice.base.spring.observability

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.observation.ServerRequestObservationConvention

@Configuration
class ObservabilityBeanConfig {
    @Bean
    fun customServerRequestObservationConvention(): ServerRequestObservationConvention =
        CustomServerRequestObservationConvention()
}