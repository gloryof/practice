package jp.glory.practice.eventStoreDb.user.web

import jp.glory.practice.eventStoreDb.user.usecase.command.ChangeNameUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/{id}/change-name")
class ChangeNameApi(
    private val useCase: ChangeNameUseCase
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
        val name: String
    ) {
        fun toInput(userId: String): ChangeNameUseCase.Input =
            ChangeNameUseCase.Input(
                userId = userId,
                name = name
            )
    }
}