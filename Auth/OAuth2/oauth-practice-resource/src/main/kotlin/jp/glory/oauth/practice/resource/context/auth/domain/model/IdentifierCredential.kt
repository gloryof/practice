package jp.glory.oauth.practice.resource.context.auth.domain.model

import jp.glory.oauth.practice.resource.base.PasswordEncryptor
import jp.glory.oauth.practice.resource.context.user.domain.model.UserId

data class IdentifierCredential(
    val loginId: String,
    val password: EncryptedPassword
)

data class EncryptedPassword(
    val value: PasswordEncryptor.EncryptedValue
)