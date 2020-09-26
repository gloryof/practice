package jp.glory.goods.app.config.spring

import jp.glory.goods.app.base.api.GoodsScope
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy


@KeycloakConfiguration
class SecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val provider = keycloakAuthenticationProvider()
        provider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(provider)
    }

    @Bean
    fun keycloakConfigResolver(): KeycloakSpringBootConfigResolver =
        KeycloakSpringBootConfigResolver()

    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy =
        NullAuthenticatedSessionStrategy()

    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http.authorizeRequests()
            .antMatchers("/api/goods").hasAuthority(GoodsScope.read)
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .anyRequest()
            .denyAll()
    }
}