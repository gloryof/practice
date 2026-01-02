package jp.glory.practice.boot.app.auth.command.domain.event

interface AuthEventHandler {
    fun handleTokenIssued(event: TokenIssued)
}
