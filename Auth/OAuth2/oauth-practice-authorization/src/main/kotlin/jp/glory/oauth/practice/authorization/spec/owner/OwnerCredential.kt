package jp.glory.oauth.practice.authorization.spec.owner

object OwnerCredential {
    private const val validOwnerId: String = "test-owner-id"
    private const val validOwnerPassword: String = "test-password"

    fun isMatch(
        ownerId: String,
        password: String
    ): Boolean =
        (ownerId == validOwnerId)
            && (password == validOwnerPassword)
}