package jp.glory.oauth.practice.resource.context.user.view

import jp.glory.oauth.practice.resource.base.view.UserViewPath
import jp.glory.oauth.practice.resource.context.user.usecase.RegisterUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class UserRegisterController(
    private val registerUser: RegisterUser
) {
    @GetMapping(UserViewPath.registerUser)
    fun view(): String = "user/register"

    @PostMapping(UserViewPath.registerUser)
    fun register(
        request: RegisterRequest,
        model: Model
    ): String =
        registerUser.register(
            name = request.name,
            plainPassword = request.plainText
        )
            .let { model.addAttribute("id",it.id) }
            .let { "user/finish" }

    data class RegisterRequest(
        val name: String,
        val plainText: String
    )
}