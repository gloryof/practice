package jp.glory.ci.cd.practice.app.auth.domain.model

class CredentialUserId(val value: String) {
    init {
        require(value.isNotEmpty())
    }

    fun match(other: CredentialUserId): Boolean =
        this.value == other.value
}