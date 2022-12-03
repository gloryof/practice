package jp.glory.boot3practice.base.spring.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration(proxyBeanMethods = false)
class WebConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { spec ->
            spec.pathMatchers("/api/*").permitAll()
        }
        http.csrf().disable()
        return http.build()
    }

    @Bean
    fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder =
        Jackson2ObjectMapperBuilder()
            .modules(JavaTimeModule())
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}