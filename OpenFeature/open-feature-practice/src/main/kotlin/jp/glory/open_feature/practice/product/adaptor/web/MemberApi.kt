package jp.glory.open_feature.practice.product.adaptor.web

import com.github.michaelbull.result.mapBoth
import jp.glory.open_feature.practice.base.adaptor.web.EndpointConst
import jp.glory.open_feature.practice.base.adaptor.web.WebApi
import jp.glory.open_feature.practice.base.adaptor.web.WebExceptionHelper
import jp.glory.open_feature.practice.product.use_case.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate

@WebApi
@RequestMapping(EndpointConst.Product.member)
class MemberApi(
    private val findMember: FindMemberUseCase
) {

    @GetMapping
    fun findAllMembers(): Members =
        findMember.findAll()
            .let { it.results.map { Member -> Member(Member) } }
            .let { Members(it) }

    @GetMapping("/{id}")
    fun findMember(
        @PathVariable("id") id: String,
    ): Member =
        findMember.findById(id)
            .mapBoth(
                success = { Member(it) },
                failure = { throw WebExceptionHelper.create(it) }
            )

    class Members(
        val Members: List<Member>
    )

    data class Member(
        val id: String,
        val givenName: String,
        val familyName: String,
        val birthDay: LocalDate
    ) {
        constructor(result: MemberSearchResult) : this(
            id = result.id,
            givenName = result.givenName,
            familyName = result.familyName,
            birthDay = result.birthDay,
        )
    }
}
