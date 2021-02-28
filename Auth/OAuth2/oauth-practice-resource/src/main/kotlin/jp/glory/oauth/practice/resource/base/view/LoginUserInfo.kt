package jp.glory.oauth.practice.resource.base.view

import jp.glory.oauth.practice.resource.context.auth.usecase.FindIdentifierCredential
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class LoginUserInfo(
    private val result: FindIdentifierCredential.CredentialResult
): UserDetails {
    val loginId = result.loginId

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf()

    override fun getPassword(): String = result.encryptedPassword

    override fun getUsername(): String = result.loginId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean  = true

    override fun isEnabled(): Boolean = true
}