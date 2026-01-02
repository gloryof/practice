package jp.glory.practice.boot.app.auth.command.infra.event

import jp.glory.practice.boot.app.auth.command.domain.event.AuthEventHandler
import jp.glory.practice.boot.app.auth.command.domain.event.TokenIssued
import jp.glory.practice.boot.app.auth.data.TokenDao
import jp.glory.practice.boot.app.auth.data.TokenRecord

class AuthEventHandlerImpl(
    private val tokenDao: TokenDao
) : AuthEventHandler {
    override fun handleTokenIssued(event: TokenIssued) =
        toTokenRecord(event)
            .let { tokenDao.insert(it) }

    private fun toTokenRecord(event: TokenIssued): TokenRecord =
        TokenRecord(
            userId = event.userId.value,
            token = event.token.value,
            expiredAt = event.token.expiresAt
        )
}
