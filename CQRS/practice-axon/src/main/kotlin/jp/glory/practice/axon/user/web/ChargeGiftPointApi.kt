package jp.glory.practice.axon.user.web

import jp.glory.practice.axon.user.usecase.command.ChargeGiftPointUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/{id}/charge-gift-point")
class ChargeGiftPointApi(
    private val useCase: ChargeGiftPointUseCase
) {
    @PostMapping
    fun change(
        @PathVariable id: String,
        @RequestBody request: Request
    ): ResponseEntity<Any> =
        request.toInput(id)
            .let { useCase.charge(it) }
            .let { ResponseEntity.noContent().build() }

    class Request(
        val amount: UInt
    ) {
        fun toInput(userId: String): ChargeGiftPointUseCase.Input =
            ChargeGiftPointUseCase.Input(
                userId = userId,
                amount = amount
            )
    }
}