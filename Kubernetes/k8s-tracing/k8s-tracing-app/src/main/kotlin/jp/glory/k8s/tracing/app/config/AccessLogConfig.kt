package jp.glory.k8s.tracing.app.config

import ch.qos.logback.access.tomcat.LogbackValve
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AccessLogConfig {
    @Bean
    fun servletContainer(): TomcatServletWebServerFactory {
        val tomcat = TomcatServletWebServerFactory()
        tomcat.addContextValves(LogbackValve())
        return tomcat
    }
}