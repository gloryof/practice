package jp.glory.ci.cd.practice.app.auth.web

import org.springframework.http.ResponseEntity
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/csrf/token")
class AuthenticateCsrfTokenApi(
    private val repository: CsrfTokenRepository
) {
    @PostMapping
    fun authenticate(
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val token = repository.generateToken(request)
        repository.saveToken(token, request, null)
        return ResponseEntity.ok(token.token)
    }
}