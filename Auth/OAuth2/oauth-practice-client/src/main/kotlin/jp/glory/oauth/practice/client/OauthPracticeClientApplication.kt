package jp.glory.oauth.practice.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class OauthPracticeClientApplication

fun main(args: Array<String>) {
	runApplication<OauthPracticeClientApplication>(*args)
}
