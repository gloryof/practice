package jp.glory.oauth.practice.authorization.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/authorize")
class AuthorizeApi {

    @GetMapping("/code")
    fun authorizeByCode(
        @RequestParam("response_type") responseType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("scope") scope: String,
        @RequestParam("state")  state: String
    ): ResponseEntity<Unit> {
        val code = UUID.randomUUID().toString()
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("$redirectUri?code=$code&state=$state"))
            .build()
    }

    @GetMapping("/implicit")
    fun authorizeImplicit(
        @RequestParam("response_type") responseType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("scope") scope: String,
        @RequestParam("state")  state: String
    ): ResponseEntity<Unit> {
        val accessToken = UUID.randomUUID().toString()
        val tokenType = "Bearer"
        val expiresIn = 3600
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(
                URI.create("$redirectUri#access_token=$accessToken?state=$state&token_type=$tokenType&expires_in=$expiresIn")
            )
            .build()
     }
}