package jp.glory.ci.cd.practice.app.base.spring.auth

import com.github.michaelbull.result.mapBoth
import jp.glory.ci.cd.practice.app.auth.usecase.AuthenticateToken
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class PreAuthenticationService(
    private val authenticateToken: AuthenticateToken
) : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken): UserDetails =
        authenticateToken(getToken(token))

    private fun getToken(token: PreAuthenticatedAuthenticationToken): String {
        val credential = token.credentials as String?
        return credential
            ?.let { parseToken(it) }
            ?: throw createException()
    }

    private fun parseToken(value: String): String? {
        if (value.isEmpty()) {
            return null
        }

        val splitValues = value.split(" ")
        if (splitValues.size < 2) {
            return null
        }

        if (splitValues[0].trim() != "Bearer") {
            return null
        }

        return splitValues[1].trim()
    }

    private fun authenticateToken(
        tokenValue: String
    ): User =
        authenticateToken.authenticate(
            AuthenticateToken.Input(tokenValue)
        )
            .mapBoth(
                success = {
                    User(
                        it.familyName + " " + it.givenName,
                        "",
                        emptyList()
                    )
                },
                failure = { throw createException() }
            )

    private fun createException(): UsernameNotFoundException =
        UsernameNotFoundException("Authentication is failed.")
}