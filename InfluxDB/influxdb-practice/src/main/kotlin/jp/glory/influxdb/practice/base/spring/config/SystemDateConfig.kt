package jp.glory.influxdb.practice.base.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class SystemDateConfig {
    @Bean
    fun systemClock(): Clock = Clock.systemUTC()
}