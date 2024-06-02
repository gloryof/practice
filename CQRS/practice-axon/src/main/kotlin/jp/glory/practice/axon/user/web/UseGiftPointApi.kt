package jp.glory.practice.axon.user.web

import jp.glory.practice.axon.user.usecase.command.UseGiftPointUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/{id}/use-gift-point")
class UseGiftPointApi(
    private val useCase: UseGiftPointUseCase
) {
    @PostMapping
    fun change(
        @PathVariable id: String,
        @RequestBody request: Request
    ): ResponseEntity<Any> =
        request.toInput(id)
            .let { useCase.use(it) }
            .let { ResponseEntity.noContent().build() }

    class Request(
        val amount: UInt
    ) {
        fun toInput(userId: String): UseGiftPointUseCase.Input =
            UseGiftPointUseCase.Input(
                userId = userId,
                amount = amount
            )
    }
}