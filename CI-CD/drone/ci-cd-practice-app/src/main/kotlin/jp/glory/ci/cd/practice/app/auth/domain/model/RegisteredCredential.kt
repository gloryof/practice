package jp.glory.ci.cd.practice.app.auth.domain.model

data class RegisteredCredential(
    val credentialUserId: CredentialUserId,
    val givenName: CredentialGivenName,
    val familyName: CredentialFamilyName,
    private val password: Password
) {
    fun authenticate(
        credentialUserId: CredentialUserId,
        password: Password
    ): Boolean =
        this.credentialUserId.match(credentialUserId) &&
            this.password.match(password)
}

class Password(private val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length >= 20)
    }

    fun match(other: Password): Boolean =
        this.value == other.value
}


@JvmInline
value class CredentialGivenName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 50)
    }
}

@JvmInline
value class CredentialFamilyName(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 50)
    }
}