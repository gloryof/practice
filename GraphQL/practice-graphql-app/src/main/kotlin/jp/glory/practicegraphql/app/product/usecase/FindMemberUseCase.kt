package jp.glory.practicegraphql.app.product.usecase

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import jp.glory.practicegraphql.app.base.usecase.UseCaseError
import jp.glory.practicegraphql.app.base.usecase.toUseCaseError
import jp.glory.practicegraphql.app.product.domain.model.Member
import jp.glory.practicegraphql.app.product.domain.model.MemberID
import jp.glory.practicegraphql.app.product.domain.model.ProductID
import jp.glory.practicegraphql.app.product.domain.repository.MemberRepository
import java.time.LocalDate

class FindMemberUseCase(
    private val repository: MemberRepository
) {
    fun findById(id: String): Result<SearchMemberResult?, UseCaseError> =
        repository.findById(MemberID(id))
            .map { toResult(it) }
            .mapError { toUseCaseError(it) }

    fun findByIds(ids: List<String>): Result<SearchMemberResults, UseCaseError> =
        repository.findByIds(ids.map { MemberID(it) })
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }

    private fun toResults(members: List<Member>): SearchMemberResults =
        SearchMemberResults(members.map { SearchMemberResult(it) })

    private fun toResult(member: Member?): SearchMemberResult? =
        member?.let{ SearchMemberResult(it) }
}

data class SearchMemberResults(
    val results: List<SearchMemberResult>
)
data class SearchMemberResult(
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