package jp.glory.oauth.practice.resource.system

import jp.glory.oauth.practice.resource.base.PasswordEncryptor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordBcryptEncoder : PasswordEncryptor {
    private val encryptor = BCryptPasswordEncoder()
    override fun encrypt(value: String): PasswordEncryptor.EncryptedValue =
        encryptor.encode(value)
            .let { PasswordEncryptor.EncryptedValue(it) }

}