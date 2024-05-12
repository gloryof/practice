package jp.glory.practice.flipt.controller

import dev.openfeature.sdk.Client
import dev.openfeature.sdk.MutableContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/context")
class ContextController(
    private val client: Client
) {
    companion object {
        private const val TARGET_KEY = "api_contextController"
    }
    @GetMapping
    fun get(
        @RequestParam id: String
    ): Response {
        val context = MutableContext()
            .apply {
                targetingKey = "${TARGET_KEY}_targeting_key"
                add("context_id", id)
            }
        val isEnable = client.getBooleanValue(TARGET_KEY, false, context)

        if (!isEnable) {
            throw IllegalStateException("Not enabled")
        }

        return Response("context")
    }

    data class Response(
        val message: String
    )
}