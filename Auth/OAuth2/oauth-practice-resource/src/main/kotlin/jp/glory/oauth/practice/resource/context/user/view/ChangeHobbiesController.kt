package jp.glory.oauth.practice.resource.context.user.view

import jp.glory.oauth.practice.resource.base.map
import jp.glory.oauth.practice.resource.base.view.LoginUserInfo
import jp.glory.oauth.practice.resource.base.view.UserViewPath
import jp.glory.oauth.practice.resource.context.user.usecase.ChangeHobby
import jp.glory.oauth.practice.resource.context.user.usecase.FindUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ChangeHobbiesController(
    private val findUser: FindUser,
    private val changeHobby: ChangeHobby
) {
    @GetMapping(UserViewPath.viewHobbies)
    fun view(
        @AuthenticationPrincipal loginUserInfo: LoginUserInfo,
        model: Model
    ): String =
        findUser.findUserInfo(loginUserInfo.loginId)
            .map {
                ViewHobbiesResponse(
                    name = it.name,
                    hobbies = createHobbiesList(it.hobbies)
                )
            }
            .map {
                model.addAttribute("info", it)
                "user/hobbies/view"
            }
            .throwIfLeft { throw IllegalStateException("User is not login") }

    @PostMapping(UserViewPath.changeHobbies)
    fun change(
        request: ChangeHobbiesRequest,
        @AuthenticationPrincipal loginUserInfo: LoginUserInfo,
        model: Model
    ): String =
        changeHobby.change(
            userId = loginUserInfo.loginId,
            hobbies = request.hobbies
        )
            .map { "redirect:${UserViewPath.viewUser}" }
            .throwIfLeft { throw RuntimeException("Update failed.") }

    private fun createHobbiesList(base: List<String>): List<String> {
        val result = MutableList(5,) {""}

        for (i in 0..base.lastIndex) {
            result[i] = base[i]
        }

        return result.toList()
    }

    data class ViewHobbiesResponse(
        val name: String,
        val hobbies: List<String>
    )

    data class ChangeHobbiesRequest(
        val hobbies: List<String>
    )
}