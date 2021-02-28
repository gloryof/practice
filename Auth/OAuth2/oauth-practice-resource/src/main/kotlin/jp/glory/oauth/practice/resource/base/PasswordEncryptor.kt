package jp.glory.oauth.practice.resource.base

interface PasswordEncryptor {
    fun encrypt(value: String): EncryptedValue

    data class EncryptedValue(val value: String)
}