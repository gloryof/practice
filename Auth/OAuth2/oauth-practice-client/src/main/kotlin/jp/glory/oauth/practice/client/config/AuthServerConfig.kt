package jp.glory.oauth.practice.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("client.auth.server")
@ConstructorBinding
data class AuthServerConfig(
    val url: String
)