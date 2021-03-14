package jp.glory.oauth.practice.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConfigurationProperties("client.resource")
@ConstructorBinding
data class ResourceServerConfig(
    val url: String
)