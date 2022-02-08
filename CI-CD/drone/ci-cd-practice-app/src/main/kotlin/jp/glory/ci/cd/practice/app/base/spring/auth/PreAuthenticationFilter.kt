package jp.glory.ci.cd.practice.app.base.spring.auth

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class PreAuthenticationFilter(
    authenticationManager: AuthenticationManager
) : AbstractPreAuthenticatedProcessingFilter() {
    init {
        super.setAuthenticationManager(authenticationManager)
    }
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any = ""

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any =
        request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
}