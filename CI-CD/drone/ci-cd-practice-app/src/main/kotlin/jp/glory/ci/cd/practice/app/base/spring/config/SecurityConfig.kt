package jp.glory.ci.cd.practice.app.base.spring.config

import jp.glory.ci.cd.practice.app.auth.usecase.AuthenticateToken
import jp.glory.ci.cd.practice.app.base.spring.auth.PreAuthenticationFilter
import jp.glory.ci.cd.practice.app.base.spring.auth.PreAuthenticationService
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun preAuthenticationService(
        authenticateToken: AuthenticateToken
    ): PreAuthenticationService =
        PreAuthenticationService(authenticateToken)

    @Bean
    fun preAuthenticatedAuthenticationProvider(
        authenticateToken: AuthenticateToken
    ): PreAuthenticatedAuthenticationProvider =
        PreAuthenticatedAuthenticationProvider()
            .apply { setPreAuthenticatedUserDetailsService(preAuthenticationService(authenticateToken)) }
            .apply { setUserDetailsChecker(AccountStatusUserDetailsChecker()) }

    @Bean
    fun preAuthenticationFilter(): PreAuthenticationFilter =
        PreAuthenticationFilter(authenticationManager())

    override fun configure(http: HttpSecurity) {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .mvcMatchers("/authenticate").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilter(preAuthenticationFilter())
            .formLogin{ it.disable() }
            .csrf { it.disable() }
    }
}