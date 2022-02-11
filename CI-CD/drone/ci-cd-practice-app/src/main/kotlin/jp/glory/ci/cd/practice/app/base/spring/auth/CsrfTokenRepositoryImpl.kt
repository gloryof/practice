package jp.glory.ci.cd.practice.app.base.spring.auth

import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.DefaultCsrfToken
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CsrfTokenRepositoryImpl : CsrfTokenRepository {
    companion object {
        private const val HEADER_NAME = "X-CSRF-TOKEN"
        private const val PARAM_NAME = "_csrf"
    }
    private val tokens: MutableMap<String, CsrfToken> = mutableMapOf()

    override fun generateToken(request: HttpServletRequest?): CsrfToken =
        createToken(UUID.randomUUID().toString())

    override fun saveToken(token: CsrfToken?, request: HttpServletRequest?, response: HttpServletResponse?) {
        token
            ?.let { tokens[it.token] = it }
    }

    override fun loadToken(request: HttpServletRequest?): CsrfToken? =
        request
            ?.getHeader(HEADER_NAME)
            ?.let { createToken(it) }

    private fun createToken(value: String): CsrfToken =
        DefaultCsrfToken(HEADER_NAME, PARAM_NAME, value)
}