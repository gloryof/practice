package jp.glory.oauth.practice.authorization.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/token")
class TokenApi {
    @PostMapping("/code")
    fun generateByCode(
        @RequestBody request: CodeRequest,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<CodeResponse> =
        CodeResponse(
            accessToken = UUID.randomUUID().toString(),
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = UUID.randomUUID().toString(),
        )
            .let { ResponseEntity.ok(it) }

    data class CodeRequest(
        val grantType: String,
        val redirectUrl: String,
        val clientId: String
    )

    data class CodeResponse(
        val accessToken: String,
        val tokenType: String,
        val expiresIn: Long,
        val refreshToken: String,
    )
}