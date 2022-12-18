package jp.glory.boot3practice.base.spring.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.spring.auth.CustomAuthUserDetailService
import jp.glory.boot3practice.base.spring.auth.CustomServerAuthenticationEntryPoint
import jp.glory.boot3practice.base.spring.auth.CustomServerAuthenticationFailureHandler
import jp.glory.boot3practice.base.spring.auth.CustomizedAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
class WebConfig {
    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        authenticationManager: ReactiveAuthenticationManager,
        objectMapper: ObjectMapper
    ): SecurityWebFilterChain {
        http
            .authorizeExchange { spec ->
                spec.pathMatchers(EndpointConst.User.register).permitAll()
                spec.pathMatchers(EndpointConst.Auth.authenticate).permitAll()
                spec.anyExchange().authenticated()
            }
        http.csrf().disable()
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        http.exceptionHandling()
            .authenticationEntryPoint(CustomServerAuthenticationEntryPoint(objectMapper))

        createAuthenticationWebFilter(
            authenticationManager = authenticationManager,
            objectMapper = objectMapper
        )
            .also { http.addFilterAt(it, SecurityWebFiltersOrder.AUTHENTICATION) }
        return http.build()
    }

    @Bean
    fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder =
        Jackson2ObjectMapperBuilder()
            .modules(JavaTimeModule())
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Bean
    fun userDetailsRepositoryReactiveAuthenticationManager(
        customAuthUserDetailService: CustomAuthUserDetailService,
        passwordEncoder: BCryptPasswordEncoder
    ): UserDetailsRepositoryReactiveAuthenticationManager =
        UserDetailsRepositoryReactiveAuthenticationManager(customAuthUserDetailService)
            .apply {
                setPasswordEncoder(passwordEncoder)
            }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder =
        BCryptPasswordEncoder(10)

    private fun createAuthenticationWebFilter(
        authenticationManager: ReactiveAuthenticationManager,
        objectMapper: ObjectMapper
    ): AuthenticationWebFilter =
        AuthenticationWebFilter(authenticationManager)
            .apply {
                setServerAuthenticationConverter(CustomizedAuthenticationConverter())
                setAuthenticationFailureHandler(
                    CustomServerAuthenticationFailureHandler(objectMapper)
                )
            }

}