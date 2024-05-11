package jp.glory.practice.flipt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/context")
class ContextController {
    @GetMapping
    fun get(): Response =
        Response("context")

    data class Response(
        val message: String
    )
}