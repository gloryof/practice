package jp.glory.boot3practice.base.spring.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jp.glory.boot3practice.base.adaptor.web.WebAuthenticationFailedError
import jp.glory.boot3practice.base.adaptor.web.WebErrorDetail
import jp.glory.boot3practice.base.adaptor.web.WebGenericErrorDetail
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import reactor.core.publisher.Mono

class CustomServerAuthenticationFailureHandler(
    private val objectMapper: ObjectMapper
) : ServerAuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange,
        exception: AuthenticationException
    ): Mono<Void> {
        val exchange = webFilterExchange.exchange
        val errorResponse = createWebErrorDetail(exception)
            .toErrorResponse(
                exchange = exchange,
                exception = exception
            )
        val response = exchange.response
        response.headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.statusCode = HttpStatus.UNAUTHORIZED

        val bufferFactory = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))
        return response.writeWith(Mono.just(bufferFactory))
    }

    private fun createWebErrorDetail(
        exception: AuthenticationException
    ): WebErrorDetail =
        when(exception) {
            is UsernameNotFoundException -> {
                WebGenericErrorDetail.createGenericErrorDetail(
                    HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value())
                )
            }
            else -> WebAuthenticationFailedError
        }

}