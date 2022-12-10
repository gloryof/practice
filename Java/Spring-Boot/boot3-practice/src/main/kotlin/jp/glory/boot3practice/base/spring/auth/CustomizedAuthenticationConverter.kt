package jp.glory.boot3practice.base.spring.auth

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.regex.Pattern

class CustomizedAuthenticationConverter : ServerAuthenticationConverter {
    private val authorizationPattern: Pattern = Pattern.compile(
        "^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
        Pattern.CASE_INSENSITIVE
    )
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val authorization = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: ""
        if (!authorization.startsWith("Bearer")) {
            return Mono.empty()
        }
        val matcher = authorizationPattern.matcher(authorization)
        if (!matcher.matches()) {
            return Mono.empty()
        }
        val token = matcher.group("token")
        return Mono.just(UsernamePasswordAuthenticationToken.unauthenticated(token, token))
    }
}
