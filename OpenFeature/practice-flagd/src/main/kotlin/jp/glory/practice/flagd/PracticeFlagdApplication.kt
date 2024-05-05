package jp.glory.practice.flagd

import dev.openfeature.contrib.providers.flagd.FlagdProvider
import dev.openfeature.sdk.OpenFeatureAPI
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class PracticeFlagdApplication

fun main(args: Array<String>) {
	runApplication<PracticeFlagdApplication>(*args)
}

