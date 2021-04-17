package jp.glory.oauth.practice.authorization.spec.refres_token

import java.util.*

class RefreshToken(
    val value: String,
    val accessTokenValue: String
) {
    companion object {
        fun generate(
            accessTokenValue: String
        ): RefreshToken =
            RefreshToken(
                value = UUID.randomUUID().toString(),
                accessTokenValue = accessTokenValue
            )
    }
}