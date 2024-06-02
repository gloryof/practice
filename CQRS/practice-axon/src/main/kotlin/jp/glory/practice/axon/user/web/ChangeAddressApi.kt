package jp.glory.practice.axon.user.web

import jp.glory.practice.axon.user.usecase.command.ChangeAddressUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/{id}/change-address")
class ChangeAddressApi(
    private val useCase: ChangeAddressUseCase
) {
    @PostMapping
    fun change(
        @PathVariable id: String,
        @RequestBody request: Request
    ): ResponseEntity<Any> =
        request.toInput(id)
            .let { useCase.change(it) }
            .let { ResponseEntity.noContent().build() }

    class Request(
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toInput(userId: String): ChangeAddressUseCase.Input =
            ChangeAddressUseCase.Input(
                userId = userId,
                postalCode = postalCode,
                prefectureCode = prefectureCode,
                city = city,
                street = street
            )
    }
}