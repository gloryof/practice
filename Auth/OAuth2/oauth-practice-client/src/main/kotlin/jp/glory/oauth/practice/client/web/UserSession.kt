package jp.glory.oauth.practice.client.web

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import java.time.LocalDateTime

@Component
@SessionScope
data class UserSession(
    val authCode: AuthCodeAttribute = AuthCodeAttribute(),
    val implicit: ImplicitAttribute = ImplicitAttribute(),
    val owner: OwnerAttribute = OwnerAttribute(),
    val client: ClientAttribute = ClientAttribute(),
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

data class ImplicitAttribute(
    var token: String = "",
    var userId: String = ""
) {
    fun isAuthenticated(): Boolean =
        token.isNotEmpty()
                && userId.isNotEmpty()
}

data class OwnerAttribute(
    var token: String = "",
    var refreshToken: String = "",
    var userId: String = ""
) {
    fun isAuthenticated(): Boolean =
        token.isNotEmpty()
                && userId.isNotEmpty()
                && refreshToken.isNotEmpty()
}

data class ClientAttribute(
    var token: String = "",
    var refreshToken: String = "",
    var userId: String = ""
) {
    fun isAuthenticated(): Boolean =
        token.isNotEmpty()
                && userId.isNotEmpty()
                && refreshToken.isNotEmpty()

}