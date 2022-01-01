package jp.glory.ci.cd.practice.app

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/practice")
class PracticeController {
    @GetMapping
    fun get(): String = "test"
}