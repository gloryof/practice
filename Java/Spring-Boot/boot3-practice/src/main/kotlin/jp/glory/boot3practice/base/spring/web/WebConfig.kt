package jp.glory.boot3practice.base.spring.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.observation.ObservationRegistry
import jp.glory.boot3practice.base.adaptor.web.EndpointConst
import jp.glory.boot3practice.base.spring.auth.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
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
@EnableAspectJAutoProxy
class WebConfig {
    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        authenticationManager: ReactiveAuthenticationManager,
        objectMapper: ObjectMapper,
        observationRegistry: ObservationRegistry,
        metricsRegistry: MeterRegistry
    ): SecurityWebFilterChain {
        http
            .authorizeExchange { spec ->
                spec.pathMatchers(EndpointConst.User.register).permitAll()
                spec.pathMatchers(EndpointConst.User.bulkRegister).permitAll()
                spec.pathMatchers(EndpointConst.Auth.authenticate).permitAll()
                spec.pathMatchers("/actuator/**").permitAll()
                spec.anyExchange().authenticated()
            }
        http.csrf {
            it.disable()
        }
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        http.exceptionHandling {
            it.authenticationEntryPoint(CustomServerAuthenticationEntryPoint(objectMapper))
        }

        createAuthenticationWebFilter(
            authenticationManager = authenticationManager,
            objectMapper = objectMapper
        )
            .also { http.addFilterAt(it, SecurityWebFiltersOrder.AUTHENTICATION) }
/*        http.addFilterAt(
            ServerHttpObservationFilter(observationRegistry),
            SecurityWebFiltersOrder.REACTOR_CONTEXT
        )

*/
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
                setAuthenticationSuccessHandler(CustomServerAuthenticationSuccessHandler())
            }

}