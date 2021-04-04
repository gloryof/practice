package jp.glory.oauth.practice.authorization.spec

enum class GrantedClient(
    val id: String
) {
    OwnClient("own-client");

    companion object {
        fun codeFrom(value: String): GrantedClient? =
            values().first { it.id == value }
    }
}