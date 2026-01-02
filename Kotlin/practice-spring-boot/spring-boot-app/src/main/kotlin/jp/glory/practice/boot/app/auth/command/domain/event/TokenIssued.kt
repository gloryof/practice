package jp.glory.practice.boot.app.auth.command.domain.event

import jp.glory.practice.boot.app.auth.command.domain.model.AuthToken
import jp.glory.practice.boot.app.user.command.domain.model.UserId

class TokenIssued(
    val userId: UserId,
    val token: AuthToken
)
