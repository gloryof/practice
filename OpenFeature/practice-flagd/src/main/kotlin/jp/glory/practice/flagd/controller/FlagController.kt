package jp.glory.practice.flagd.controller

import dev.openfeature.contrib.providers.flagd.FlagdProvider
import dev.openfeature.sdk.ImmutableContext
import dev.openfeature.sdk.OpenFeatureAPI
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/flag")
class FlagController(
    private val flagdProvider: FlagdProvider
) {
    @GetMapping
    fun get(): Response {
        val isEnable = flagdProvider.getBooleanEvaluation("flagController", false, ImmutableContext())
            .value

        if (!isEnable) {
            throw UnsupportedOperationException("not supported")
        }
        return Response("flag")
    }

    data class Response(
        val message: String
    )
}