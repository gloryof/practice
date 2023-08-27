package jp.glory.gradle.practice.api

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("test")
class TestApi {

    @GetMapping("{value}")
    fun calculateValue(
        @PathVariable value: Int
    ): ResponseEntity<Any> =
        validate(value)
            .map { value * 2 }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { ResponseEntity.badRequest().body("Value is not unsigned int") }
            )

    private fun validate(value: Int): Result<ULong, Unit> =
        if (value < 0) {
            Err(Unit)
        } else {
            Ok(value.toULong())
        }
}