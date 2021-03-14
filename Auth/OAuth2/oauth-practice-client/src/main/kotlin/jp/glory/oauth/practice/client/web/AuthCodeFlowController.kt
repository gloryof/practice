package jp.glory.oauth.practice.client.web

import jp.glory.oauth.practice.client.base.map
import jp.glory.oauth.practice.client.config.ServerConfig
import jp.glory.oauth.practice.client.lib.AuthServerClient
import jp.glory.oauth.practice.client.lib.ResourceSeverClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/code")
class AuthCodeFlowController(
    private val authServerClient: AuthServerClient,
    private val resourceSeverClient: ResourceSeverClient,
    private val serverConfig: ServerConfig,
    private val userSession: UserSession
) {

    @GetMapping("/authorized")
    fun authorized(
        @RequestParam code: String,
        @RequestParam state: String
    ): String {
        authServerClient.generateTokenByCode(code)
            .map {
                userSession.authCode.token = it.accessToken
                userSession.authCode.refreshToken = it.refreshToken
                userSession.authCode.userId = it.userId
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }

        return "redirect:/code/user/view"
    }

    @GetMapping("/user/view")
    fun viewUser(
        model: Model
    ): String {
        val authAttribute = userSession.authCode

        if (!authAttribute.isAuthenticated()) {
            val redirectUrl = resourceSeverClient.generateLoginUrl(serverConfig.url)
            return "redirect:$redirectUrl"
        }

        return resourceSeverClient
            .findById(
                accessToken = authAttribute.token,
                id = authAttribute.userId
            )
            .map {
                UserInfo(
                    id = it.id,
                    name = it.name,
                    hobbies = it.hobbies
                )
            }
            .map {
                UserViewInfo(
                    user = it,
                    mode = Mode.CODE,
                )
            }
            .map { model.addAttribute("info", it) }
            .map { "user/view" }
            .throwIfLeft { throw IllegalStateException("User is not login") }
    }
}