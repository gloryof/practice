package jp.glory.ktor.practice.auth.domain.repository

import jp.glory.ktor.practice.auth.domain.event.TokenAuthenticateEvent
import jp.glory.ktor.practice.auth.domain.model.Token

interface TokenRepository {
    fun save(
        token: Token
    )
    fun findToken(event: TokenAuthenticateEvent): Token?
}
