package jp.glory.practice.flagd

import dev.openfeature.contrib.providers.flagd.Config
import dev.openfeature.contrib.providers.flagd.FlagdOptions
import dev.openfeature.contrib.providers.flagd.FlagdProvider
import dev.openfeature.sdk.OpenFeatureAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun flagdProvider(): FlagdProvider {
        val option = FlagdOptions.builder()
            .host("localhost")
            .port(30085)
            .build()
        return FlagdProvider(option)
    }
}