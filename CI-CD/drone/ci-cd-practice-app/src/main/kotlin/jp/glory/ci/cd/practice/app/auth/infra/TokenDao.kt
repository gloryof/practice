package jp.glory.ci.cd.practice.app.auth.infra

import org.springframework.stereotype.Component

@Component
class TokenDao {
    private val tokens = mutableMapOf<String, TokenInfo>()
    fun save(token :TokenInfo) {
        tokens[token.tokenValue] = token
    }
    fun findByToken(tokenValue: String): TokenInfo? = tokens[tokenValue]
    fun deleteByUserId(userId: String) {
        val targetKeys = tokens
            .filter { it.value.credentialUserId == userId }
            .keys
        targetKeys.forEach { tokens.remove(it) }
    }
}

class TokenInfo(
    val credentialUserId: String,
    val tokenValue: String
)