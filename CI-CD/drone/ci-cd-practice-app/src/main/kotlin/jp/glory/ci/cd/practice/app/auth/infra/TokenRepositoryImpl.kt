package jp.glory.ci.cd.practice.app.auth.infra

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.domain.model.CredentialUserId
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredCredential
import jp.glory.ci.cd.practice.app.auth.domain.model.RegisteredToken
import jp.glory.ci.cd.practice.app.auth.domain.model.TokenValue
import jp.glory.ci.cd.practice.app.auth.domain.repository.TokenRepository
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError
import org.springframework.stereotype.Repository

@Repository
class TokenRepositoryImpl(
    private val dao: TokenDao
) : TokenRepository {
    override fun save(
        tokenValue: TokenValue,
        registeredCredential: RegisteredCredential
    ): Result<RegisteredToken, DomainUnknownError> {
        val tokenInfo = TokenInfo(
            credentialUserId = registeredCredential.credentialUserId.value,
            tokenValue = tokenValue.value
        )
        dao.save(tokenInfo)
        return Ok(toRegisteredToken(tokenInfo))
    }

    override fun findToken(
        tokenValue: TokenValue
    ): Result<RegisteredToken?, DomainUnknownError> =
        dao.findByToken(tokenValue.value)
            ?.let { toRegisteredToken(it) }
            .let { Ok(it) }

    private fun toRegisteredToken(
        tokenInfo: TokenInfo
    ): RegisteredToken =
        RegisteredToken(
            credentialUserId = CredentialUserId(tokenInfo.credentialUserId),
            tokenValue = TokenValue(tokenInfo.tokenValue)
        )
}