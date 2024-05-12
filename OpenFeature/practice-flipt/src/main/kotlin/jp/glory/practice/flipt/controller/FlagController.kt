package jp.glory.practice.flipt.controller

import dev.openfeature.sdk.Client
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/flag")
class FlagController(
    private val client: Client
) {
    @GetMapping
    fun get(): Response {
        val isEnable = client.getBooleanValue("flagController", false)

        if (!isEnable) {
            throw IllegalStateException("Not enabled")
        }
        return Response("flag")
    }

    data class Response(
        val message: String
    )
}