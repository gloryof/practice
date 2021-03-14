package jp.glory.oauth.practice.client.web

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@Component
@SessionScope
data class UserSession(
    val authCode: AuthCodeAttribute = AuthCodeAttribute()
)

data class AuthCodeAttribute(
    var token: String = "",
    var refreshToken: String = "",
    var userId: String = ""
) {
    fun isAuthenticated(): Boolean =
        token.isNotEmpty()
                && userId.isNotEmpty()
                && refreshToken.isNotEmpty()

}