package jp.glory.boot3practice.base.spring.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.web.ErrorResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class CustomServerAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : ServerAuthenticationEntryPoint {
    override fun commence(
        exchange: ServerWebExchange,
        ex: AuthenticationException
    ): Mono<Void> {
        val errorResponse = createErrorResponse(ex)
        val response = exchange.response
        response.headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.statusCode = HttpStatus.UNAUTHORIZED

        val bufferFactory = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))
        return response.writeWith(Mono.just(bufferFactory))
    }

    private fun createErrorResponse(
        ex: AuthenticationException
    ): ErrorResponse =
        ErrorResponse.builder(
            ex,
            HttpStatus.UNAUTHORIZED,
            "Authorization is failed"
        )
            .build()
}