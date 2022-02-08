package jp.glory.ci.cd.practice.app.auth.infra

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredCredential
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredToken
import jp.glory.ci.cd.practice.app.auth.domain.model.TokenValue
import jp.glory.ci.cd.practice.app.auth.domain.repository.TokenRepository
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl : TokenRepository {
    private val tokens: MutableMap<String, RegisteredToken>  = mutableMapOf()

    override fun save(
        tokenValue: TokenValue,
        registeredCredential: RegisteredCredential
    ): Result<RegisteredToken, DomainUnknownError> =
        RegisteredToken(
            credentialUserId = registeredCredential.credentialUserId,
            tokenValue = tokenValue
        )
            .also { tokens[tokenValue.value] = it }
            .let { Ok(it) }

    override fun findToken(
        tokenValue: TokenValue
    ): Result<RegisteredToken?, DomainUnknownError> =
        Ok(tokens[tokenValue.value])
}