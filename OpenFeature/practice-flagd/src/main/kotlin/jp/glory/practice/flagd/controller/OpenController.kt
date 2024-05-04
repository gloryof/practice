package jp.glory.practice.flagd.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/open")
class OpenController {
    @GetMapping
    fun get(): Response =
        Response("open")

    data class Response(
        val message: String
    )
}