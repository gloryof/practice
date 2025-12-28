package jp.glory.practice.boot.app

import jp.glory.practice.boot.app.auth.AuthBeanRegister
import jp.glory.practice.boot.app.base.spring.SystemInjectionConfig
import jp.glory.practice.boot.app.user.UserBeanRegister
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(UserBeanRegister::class, AuthBeanRegister::class, SystemInjectionConfig::class)
class SpringBootAppApplication

fun main(args: Array<String>) {
    runApplication<SpringBootAppApplication>(*args)
}
