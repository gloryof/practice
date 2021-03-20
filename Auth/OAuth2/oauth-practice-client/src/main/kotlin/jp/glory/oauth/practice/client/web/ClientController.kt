package jp.glory.oauth.practice.client.web

import jp.glory.oauth.practice.client.base.map
import jp.glory.oauth.practice.client.config.ServerConfig
import jp.glory.oauth.practice.client.lib.AuthServerClient
import jp.glory.oauth.practice.client.lib.ResourceSeverClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/client")
class ClientController(
    private val authServerClient: AuthServerClient,
    private val resourceSeverClient: ResourceSeverClient,
    private val serverConfig: ServerConfig,
    private val userSession: UserSession
) {
    @GetMapping("/login")
    fun login(): String = "user/client-login"

    @PostMapping("/login")
    fun authenticate(
        request: Request
    ): String {
        authServerClient.generateTokenByClient(
            scope = listOf("READ", "WRITE")
        )
            .map {
                userSession.client.token = it.accessToken
                userSession.client.refreshToken = it.refreshToken
                userSession.client.userId = it.userId
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }

        return "redirect:/client/user/view"
    }

    @GetMapping("/user/view")
    fun viewUser(
        model: Model
    ): String {
        val authAttribute = userSession.client

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
                    mode = Mode.CLIENT
                )
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }
    }

    data class Request(
        val userId: String
    )
}