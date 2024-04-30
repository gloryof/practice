package jp.glory.practice.pyroscope.controlle

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hello")
class HelloController {
    @GetMapping
    fun getHello(): Response =
        Response("hello")

    class Response(
        val message: String
    )
}