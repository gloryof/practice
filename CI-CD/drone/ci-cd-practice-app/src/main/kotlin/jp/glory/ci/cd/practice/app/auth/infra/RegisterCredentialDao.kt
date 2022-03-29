package jp.glory.ci.cd.practice.app.auth.infra

import org.springframework.stereotype.Component

@Component
class RegisterCredentialDao {
    private val credentials = mutableMapOf<String, RegisterCredentialInfo>()
    fun findById(credentialUserId: String): RegisterCredentialInfo? = credentials[credentialUserId]
    fun save(info: RegisterCredentialInfo) {
        credentials[info.credentialUserId] = info
    }
    fun delete(id: String) {
        credentials.remove(id)
    }
}

data class RegisterCredentialInfo(
    val credentialUserId: String,
    val givenName: String,
    val familyName: String,
    val password: String
)