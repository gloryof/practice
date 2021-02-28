package jp.glory.oauth.practice.resource.context.user.view

import jp.glory.oauth.practice.resource.base.map
import jp.glory.oauth.practice.resource.base.mapBoth
import jp.glory.oauth.practice.resource.base.view.LoginUserInfo
import jp.glory.oauth.practice.resource.base.view.UserViewPath
import jp.glory.oauth.practice.resource.context.user.usecase.FindUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserViewController(
    private val findUser: FindUser
) {

    @GetMapping(UserViewPath.viewUser)
    fun viewUserInfo(
        @AuthenticationPrincipal loginUserInfo: LoginUserInfo,
        model: Model
    ): String =
        findUser.findUserInfo(loginUserInfo.loginId)
            .map {
                UserViewInfo(
                    name = it.name,
                    hobbies = it.hobbies
                )
            }
            .map {
                model.addAttribute("info", it)
                "user/view"
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }

    data class UserViewInfo(
        val name: String,
        val hobbies: List<String>
    )
}