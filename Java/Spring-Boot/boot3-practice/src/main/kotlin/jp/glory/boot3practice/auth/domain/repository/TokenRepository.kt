package jp.glory.boot3practice.auth.domain.repository

import jp.glory.boot3practice.auth.domain.event.TokenAuthenticateEvent
import jp.glory.boot3practice.auth.domain.model.Token

interface TokenRepository {
    fun save(
        token: Token
    )
    fun findToken(event: TokenAuthenticateEvent): Token?
}