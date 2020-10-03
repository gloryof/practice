package jp.glory.goods.app.auth.api

import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(
    value = ["/api/auth"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AuthApi {
    @GetMapping("me")
    fun me(authentication: OAuth2AuthenticationToken): Any? {
        return authentication
    }
}