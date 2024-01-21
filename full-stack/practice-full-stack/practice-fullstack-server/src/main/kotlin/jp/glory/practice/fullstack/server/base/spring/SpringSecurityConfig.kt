package jp.glory.practice.fullstack.server.base.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SpringSecurityConfig {

    @Bean
    fun filterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http {
            cors {
                disable()
            }
            csrf {
                disable()
            }
            authorizeHttpRequests {
                authorize("/api/**", permitAll)
                authenticated
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
        }

        return http.build()
    }
}