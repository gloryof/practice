package jp.glory.boot3practice.base.spring.auth

import com.github.michaelbull.result.mapBoth
import jp.glory.boot3practice.auth.use_case.AuthenticateUseCase
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomAuthUserDetailService(
    private val authenticateUseCase: AuthenticateUseCase,
    private val passwordEncoder: BCryptPasswordEncoder
) : ReactiveUserDetailsService {
    override fun findByUsername(input: String?): Mono<UserDetails>? {
        val token = input ?: throw UsernameNotFoundException("Token is required.")

        return Mono.just(
            authenticateUseCase.authenticateToken(
                AuthenticateUseCase.TokenAuthInput(token)
            )
                .mapBoth(
                    success = { UserAccount(it.id, passwordEncoder.encode(token)) },
                    failure = { throw UsernameNotFoundException("Failed token authenticate.") }
                )
        )
    }
}