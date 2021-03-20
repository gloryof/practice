package jp.glory.oauth.practice.client.web

import jp.glory.oauth.practice.client.base.map
import jp.glory.oauth.practice.client.config.ServerConfig
import jp.glory.oauth.practice.client.lib.AuthServerClient
import jp.glory.oauth.practice.client.lib.ResourceSeverClient
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/owner")
class OwnerController(
    private val authServerClient: AuthServerClient,
    private val resourceSeverClient: ResourceSeverClient,
    private val serverConfig: ServerConfig,
    private val userSession: UserSession
) {
    @GetMapping("/login")
    fun login(): String = "user/owner-login"

    @PostMapping("/login")
    fun authenticate(
        request: Request
    ): String {
        authServerClient.generateTokenByOwner(
            userName = request.userId,
            password = request.password,
            scope = listOf("READ", "WRITE")
        )
            .map {
                userSession.owner.token = it.accessToken
                userSession.owner.refreshToken = it.refreshToken
                userSession.owner.userId = it.userId
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }

        return "redirect:/owner/user/view"
    }

    @GetMapping("/user/view")
    fun viewUser(
        model: Model
    ): String {
        val authAttribute = userSession.owner

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
                    mode = Mode.OWNER
                )
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }
    }

    data class Request(
        val userId: String,
        val password: String
    )
}