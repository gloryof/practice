package jp.glory.oauth.practice.authorization.spec.client

object ClientCredential {
    private const val validOwnerId: String = "test-client-id"
    private const val validOwnerPassword: String = "test-password"

    fun isMatch(
        ownerId: String,
        password: String
    ): Boolean =
        (ownerId == validOwnerId)
            && (password == validOwnerPassword)
}