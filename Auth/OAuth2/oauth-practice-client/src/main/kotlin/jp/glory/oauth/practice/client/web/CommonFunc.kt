package jp.glory.oauth.practice.client.web

import jp.glory.oauth.practice.client.lib.ResourceSeverClient
import org.springframework.ui.Model

fun prepareView(
    response: ResourceSeverClient.UserResponse,
    model: Model,
    mode: Mode
): String =
    response
        .let {
            UserInfo(
                id = it.id,
                name = it.name,
                hobbies = it.hobbies
            )
        }
        .let {
            UserViewInfo(
                user = it,
                mode = mode,
            )
        }
        .let { model.addAttribute("info", it) }
        .let { "user/view" }
