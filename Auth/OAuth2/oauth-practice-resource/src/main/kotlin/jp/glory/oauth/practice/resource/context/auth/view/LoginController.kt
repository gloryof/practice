package jp.glory.oauth.practice.resource.context.auth.view

import jp.glory.oauth.practice.resource.base.view.AuthViewPath
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping(AuthViewPath.login)
    fun view(): String = "/login"
}