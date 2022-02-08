package jp.glory.ci.cd.practice.app.auth.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredCredential
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredToken
import jp.glory.ci.cd.practice.app.auth.domain.model.TokenValue
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError

interface TokenRepository {
    fun save(
        tokenValue: TokenValue,
        registeredCredential: RegisteredCredential
    ): Result<RegisteredToken, DomainUnknownError>
    fun findToken(tokenValue: TokenValue): Result<RegisteredToken?, DomainUnknownError>
}