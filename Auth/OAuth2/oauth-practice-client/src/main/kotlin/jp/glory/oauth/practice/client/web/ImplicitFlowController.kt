package jp.glory.oauth.practice.client.web

import jp.glory.oauth.practice.client.base.map
import jp.glory.oauth.practice.client.config.ServerConfig
import jp.glory.oauth.practice.client.lib.ResourceSeverClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/implicit")
class ImplicitFlowController(
    private val resourceSeverClient: ResourceSeverClient,
    private val serverConfig: ServerConfig,
    private val userSession: UserSession
) {

    @GetMapping("/authorized")
    fun authorized(
        @RequestParam("access_token") accessToken: String,
        @RequestParam("token_type") tokenType: String,
        @RequestParam("expires_in") expiresIn: Long,
        @RequestParam("user_id") userId: String,
        @RequestParam scope: String,
        @RequestParam state: String

    ): String {
        userSession.implicit.token = accessToken
        userSession.implicit.userId = userId

        return "redirect:/implicit/user/view"
    }

    @GetMapping("/user/view")
    fun viewUser(
        model: Model
    ): String {
        val authAttribute = userSession.implicit

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
                prepareView(
                    response = it,
                    model = model,
                    mode = Mode.IMPLICIT
                )
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }
    }
}