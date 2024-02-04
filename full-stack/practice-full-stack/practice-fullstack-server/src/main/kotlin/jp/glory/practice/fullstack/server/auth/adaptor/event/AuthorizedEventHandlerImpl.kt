package jp.glory.practice.fullstack.server.auth.adaptor.event

import jp.glory.practice.fullstack.server.auth.domain.AuthorizedEvent
import jp.glory.practice.fullstack.server.auth.domain.AuthorizedEventHandler
import jp.glory.practice.fullstack.server.auth.domain.RevokeTokenEvent
import jp.glory.practice.fullstack.server.base.adaptor.store.TokenDao
import jp.glory.practice.fullstack.server.base.adaptor.store.TokenRecord

class AuthorizedEventHandlerImpl(
    private val dao: TokenDao
) : AuthorizedEventHandler {
    override fun register(event: AuthorizedEvent) {
        TokenRecord(
            token = event.token.value,
            userId = event.userId.value
        )
            .let { dao.save(it) }
    }

    override fun revoke(event: RevokeTokenEvent) {
        dao.delete(event.token.value)
    }
}