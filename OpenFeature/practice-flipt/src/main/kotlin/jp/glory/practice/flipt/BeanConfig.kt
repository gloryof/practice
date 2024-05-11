package jp.glory.practice.flipt

import dev.openfeature.contrib.providers.flipt.FliptProvider
import dev.openfeature.contrib.providers.flipt.FliptProviderConfig
import dev.openfeature.sdk.Client
import dev.openfeature.sdk.OpenFeatureAPI
import io.flipt.api.FliptClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun client(): Client {
        val clientBuilder = FliptClient
            .builder()
            .url("http://localhost:30090")
        val provider = FliptProviderConfig.builder()
            .fliptClientBuilder(clientBuilder)
            .build()
            .let { FliptProvider(it) }

        val providerName = "flpit"
        OpenFeatureAPI.getInstance().setProviderAndWait(providerName, provider)

        return OpenFeatureAPI.getInstance().getClient(providerName)
    }
}