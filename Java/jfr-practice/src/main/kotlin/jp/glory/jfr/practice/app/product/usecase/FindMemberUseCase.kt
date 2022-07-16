package jp.glory.jfr.practice.app.product.usecase

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import jp.glory.jfr.practice.app.base.usecase.UseCase
import jp.glory.jfr.practice.app.base.usecase.UseCaseError
import jp.glory.jfr.practice.app.base.usecase.toUseCaseError
import jp.glory.jfr.practice.app.product.domain.model.Member
import jp.glory.jfr.practice.app.product.domain.model.MemberID
import jp.glory.jfr.practice.app.product.domain.repository.MemberRepository
import java.time.LocalDate

@UseCase
class FindMemberUseCase(
    private val repository: MemberRepository
) {
    fun findById(id: String): Result<MemberSearchResult?, UseCaseError> =
        repository.findById(MemberID(id))
            .map { toResult(it) }
            .mapError { toUseCaseError(it) }

    fun findByIds(ids: List<String>): Result<MemberSearchResults, UseCaseError> =
        repository.findByIds(ids.map { MemberID(it) })
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }

    private fun toResults(members: List<Member>): MemberSearchResults =
        MemberSearchResults(members.map { MemberSearchResult(it) })

    private fun toResult(member: Member?): MemberSearchResult? =
        member?.let { MemberSearchResult(it) }
}

data class MemberSearchResults(
    val results: List<MemberSearchResult>
)

data class MemberSearchResult(
    val id: String,
    val givenName: String,
    val familyName: String,
    val birthDay: LocalDate
) {
    constructor(member: Member) : this(
        id = member.id.value,
        givenName = member.name.givenName.value,
        familyName = member.name.familyName.value,
        birthDay = member.birthDay
    )
}
