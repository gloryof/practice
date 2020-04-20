package jp.glory.redis.sentinel.app.api

import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
@RequestMapping("/api/redis")
class RedisApi(
    private val template: StringRedisTemplate
) {

    private val key = "tes-key"

    @PostMapping
    fun save(): ResponseEntity<Unit> {
        template.opsForValue().set(key, "test-value")
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun get(): ResponseEntity<String> {
        val result = template.opsForValue().get(key) ?: "None"
        return ResponseEntity.ok(result)
    }

}