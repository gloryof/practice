package jp.glory.jfr.practice.app.product.adaptor.web

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import jp.glory.jfr.practice.app.base.adaptor.web.error.WebExceptionHelper
import jp.glory.jfr.practice.app.product.usecase.FindMemberUseCase
import jp.glory.jfr.practice.app.product.usecase.MemberSearchResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/members")
class MemberApi(
    private val findMemberUseCase: FindMemberUseCase,
) {
    @GetMapping
    fun findAll(): ResponseEntity<MembersResponse> =
        findMemberUseCase.findAll()
            .map {
                MembersResponse(
                    members = it.results.map { result -> toMemberResponse(result) }
                )
            }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: String
    ): ResponseEntity<MemberResponse> =
        findMemberUseCase.findById(id)
            .map { toMemberResponse(it) }
            .mapBoth(
                success = { ResponseEntity.ok(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    private fun toMemberResponse(
        result: MemberSearchResult
    ): MemberResponse = MemberResponse(
        id = result.id,
        givenName = result.givenName,
        familyName = result.familyName,
        birthDay = result.birthDay,
    )
}

data class MembersResponse(
    val members: List<MemberResponse>
)

data class MemberResponse(
    val id: String,
    val givenName: String,
    val familyName: String,
    val birthDay: LocalDate
)
