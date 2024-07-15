package jp.glory.practice.axon.user.web

import jp.glory.practice.axon.user.usecase.command.CreateUserUseCase
import jp.glory.practice.axon.user.usecase.query.GetGiftHistoryUseCase
import jp.glory.practice.axon.user.usecase.query.GetUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("/api/users")
class UserApi(
    private val getUserUseCase: GetUserUseCase,
    private val createUseCase: CreateUserUseCase,
    private val getGiftHistoryUseCase: GetGiftHistoryUseCase
) {
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: String
    ): ResponseEntity<GetUserResponse> =
        getUserUseCase.findById(GetUserUseCase.Input(id))
            .let { GetUserResponse.create(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}/gift/history")
    fun getGiftHistory(
        @PathVariable id: String
    ): ResponseEntity<GetGiftPointHistoryResponse> =
        getGiftHistoryUseCase.findById(GetGiftHistoryUseCase.Input(id))
            .let { GetGiftPointHistoryResponse.create(it) }
            .let { ResponseEntity.ok(it) }

    @PostMapping
    fun create(
        @RequestBody request: CreateRequest
    ): ResponseEntity<CreateResponse> =
        request.toInput()
            .let { createUseCase.create(it) }
            .let { ResponseEntity.ok(CreateResponse(it.userId)) }

    class GetUserResponse(
        val id: String,
        val name: String,
        val postalCode: String,
        val prefectureName: String,
        val city: String,
        val street: String,
        val giftPoint: UInt
    ) {
        companion object {
            fun create(output: GetUserUseCase.Output): GetUserResponse =
                GetUserResponse(
                    id = output.userId,
                    name = output.name,
                    postalCode = output.address.postalCode,
                    prefectureName = output.address.prefectureName,
                    city = output.address.city,
                    street = output.address.street,
                    giftPoint = output.remainingGiftPoint
                )
        }
    }

    class CreateRequest(
        val name: String,
        val postalCode: String,
        val prefectureCode: String,
        val city: String,
        val street: String
    ) {
        fun toInput(): CreateUserUseCase.Input =
            CreateUserUseCase.Input(
                name = name,
                postalCode = postalCode,
                prefectureCode = prefectureCode,
                city = city,
                street = street
            )
    }

    class CreateResponse(
        val userId: String
    )

    class GetGiftPointHistoryResponse(
        val details: List<Detail>
    ) {
        companion object {
            fun create(result: GetGiftHistoryUseCase.Output): GetGiftPointHistoryResponse =
                GetGiftPointHistoryResponse(
                    details = result.details.map { Detail.create(it) },
                )
        }

        class Detail private constructor(
            val recordedAt: OffsetDateTime,
            val type: String,
            val amount: UInt
        ) {
            companion object {
                fun create(detail: GetGiftHistoryUseCase.Output.Detail): Detail =
                    Detail(
                        recordedAt = detail.recordedAt,
                        type = detail.type,
                        amount = detail.amount,
                    )
            }
        }
    }
}