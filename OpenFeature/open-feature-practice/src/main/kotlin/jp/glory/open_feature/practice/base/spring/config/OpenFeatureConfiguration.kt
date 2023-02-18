package jp.glory.open_feature.practice.base.spring.config

import dev.openfeature.sdk.OpenFeatureAPI
import jp.glory.open_feature.practice.base.open_feature.OriginalFeatureProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenFeatureConfiguration {
    @Bean
    fun OpenFeatureAPI(): OpenFeatureAPI =
        OpenFeatureAPI.getInstance()
            .apply {
                provider = OriginalFeatureProvider()
            }
}