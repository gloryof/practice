package jp.glory.goods.app.goods.api

import jp.glory.goods.app.base.api.ErrorResponse
import jp.glory.goods.app.base.api.GoodsScope
import jp.glory.goods.app.base.usecase.UseCaseErrors
import jp.glory.goods.app.goods.usecase.GoodsDetail
import jp.glory.goods.app.goods.usecase.GoodsSaveUseCase
import jp.glory.goods.app.goods.usecase.GoodsSearchResult
import jp.glory.goods.app.goods.usecase.GoodsSearchUseCase
import org.springframework.context.annotation.Role
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    value = ["/api/goods"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class GoodsApi(
    private val search: GoodsSearchUseCase,
    private val save: GoodsSaveUseCase
) {
    @GetMapping
    @PreAuthorize("hasAuthority('${GoodsScope.read}')")
    fun getAll(): ResponseEntity<GoodsSearchResponse> = ResponseEntity.ok(
        GoodsSearchResponse(search.findAll())
    )

    @PostMapping
    @PreAuthorize("hasAuthority('${GoodsScope.register}')")
    fun register(
        @RequestBody request: GoodsSaveRequest
    ): ResponseEntity<out Any> =
        save.register(
            name = request.name,
            price = request.price
        ).mapBoth(
            left = { errorResponse(it)},
            right = { successResponse(it) }
        )

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('${GoodsScope.read}')")
    fun getById(
        @PathVariable id: String
    ): ResponseEntity<GoodsDetailResponse> =
        search.findById(id)
            ?.let { GoodsDetailResponse(it) }
            ?.let { ResponseEntity.ok().body(it) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('${GoodsScope.update}')")
    fun update(
        @PathVariable id: String,
        @RequestBody request: GoodsSaveRequest
    ) =
        save.update(
            goodsId = id,
            name = request.name,
            price = request.price
        ).mapBoth(
            left = { errorResponse(it)},
            right = { successResponse(it) }
        )

    private fun successResponse(id: String): ResponseEntity<SaveResultResponse> =
        ResponseEntity.ok().body(SaveResultResponse(id))

    private fun errorResponse(errors: UseCaseErrors): ResponseEntity<ErrorResponse> =
        ErrorResponse(
            summary = "Invalid request",
            errors = errors
        )
            .let { ResponseEntity.badRequest().body(it) }
}

data class GoodsSaveRequest(
    var name: String? = null,
    var price: String? = null
)

data class GoodsSearchResponse(
    val results: List<GoodsDetailResponse>
) {
    constructor(searchResult: GoodsSearchResult) : this(
        results = searchResult.details.map { GoodsDetailResponse(it) }
    )
}

data class GoodsDetailResponse(
    val id: String,
    val name: String,
    val price: Int
) {
    constructor(detail: GoodsDetail) : this(
        id = detail.goodsId,
        name = detail.name,
        price = detail.price
    )
}

data class SaveResultResponse(
    val id: String
)