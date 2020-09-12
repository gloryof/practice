package jp.glory.kofu.app.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate


@Configuration("kofu.app.config")
class ApiClientConfig(
    @Value("url") val targetUrl: String =  "http://localhost:8080/api"
) {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()
}