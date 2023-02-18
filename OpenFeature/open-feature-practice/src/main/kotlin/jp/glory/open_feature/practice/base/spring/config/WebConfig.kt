package jp.glory.open_feature.practice.base.spring.config

import dev.openfeature.sdk.OpenFeatureAPI
import jp.glory.open_feature.practice.base.spring.interceptor.OpenFeatureInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val openFeatureAPI: OpenFeatureAPI
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(OpenFeatureInterceptor(openFeatureAPI))
    }
}