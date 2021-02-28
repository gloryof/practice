package jp.glory.oauth.practice.resource.system.spring

import jp.glory.oauth.practice.resource.base.view.AuthViewPath
import jp.glory.oauth.practice.resource.base.view.LoginUserInfo
import jp.glory.oauth.practice.resource.base.view.UserViewPath
import jp.glory.oauth.practice.resource.context.auth.usecase.FindIdentifierCredential
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
class SecurityConfig(
    private val loginService: PasswordLoginService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(UserViewPath.registerUser).permitAll()
            .anyRequest().authenticated()
            .and()
            // login
            .formLogin()
            .loginPage(AuthViewPath.login).permitAll()
            .defaultSuccessUrl(UserViewPath.viewUser)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(loginService)
            .passwordEncoder(passwordEncoder())
    }
}

@Component
class PasswordLoginService(
    private val findCredential: FindIdentifierCredential
): UserDetailsService {
    override fun loadUserByUsername(loginId: String?): UserDetails {
        if (loginId == null) throw UsernameNotFoundException("Login ID is required.")

        return loginId
            .let { findCredential.findByLoginId(it) }
            .throwIfLeft { throw UsernameNotFoundException("Can not find login user.") }
            .let { LoginUserInfo(it) }
    }
}