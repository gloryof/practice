package jp.glory.open_feature.practice.product.use_case

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import jp.glory.open_feature.practice.base.use_case.UseCase
import jp.glory.open_feature.practice.base.use_case.UseCaseError
import jp.glory.open_feature.practice.base.use_case.UseCaseNotFoundError
import jp.glory.open_feature.practice.product.domain.model.Member
import jp.glory.open_feature.practice.product.domain.model.MemberID
import jp.glory.open_feature.practice.product.domain.repository.MemberRepository
import java.time.LocalDate

@UseCase
class FindMemberUseCase(
    private val repository: MemberRepository
) {
    fun findById(id: String): Result<MemberSearchResult, UseCaseError> =
        repository.findById(MemberID(id))
            ?.let { Ok(MemberSearchResult(it)) }
            ?: Err(
                UseCaseNotFoundError(
                    resourceName = UseCaseNotFoundError.ResourceName.Member,
                    idValue = id
                )
            )

    fun findAll(): MemberSearchResults =
        repository.findAll()
            .let { toResults(it) }

    private fun toResults(members: List<Member>): MemberSearchResults =
        MemberSearchResults(members.map { MemberSearchResult(it) })

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