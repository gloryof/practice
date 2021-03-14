package jp.glory.oauth.practice.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("api")
@ConstructorBinding
data class ServerConfig(
    val url: String
)