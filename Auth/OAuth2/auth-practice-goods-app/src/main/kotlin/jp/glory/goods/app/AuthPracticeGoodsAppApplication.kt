package jp.glory.goods.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.accept.FixedContentNegotiationStrategy

import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.List


@SpringBootApplication
class AuthPracticeGoodsAppApplication

fun main(args: Array<String>) {
	runApplication<AuthPracticeGoodsAppApplication>(*args)
}