package jp.glory.boot3practice.base.spring.auth

import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import reactor.core.publisher.Mono

class CustomServerAuthenticationSuccessHandler : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange,
        authentication: Authentication
    ): Mono<Void> {
        val exchange = webFilterExchange.exchange
            .apply {
                attributes["user-id"] = (authentication.principal as UserAccount).username
            }
        return webFilterExchange.chain.filter(exchange)
    }
}