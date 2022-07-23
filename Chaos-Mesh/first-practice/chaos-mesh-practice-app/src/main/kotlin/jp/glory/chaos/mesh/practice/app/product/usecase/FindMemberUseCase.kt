package jp.glory.chaos.mesh.practice.app.product.usecase

import com.github.michaelbull.result.*
import jp.glory.chaos.mesh.practice.app.base.usecase.UseCase
import jp.glory.chaos.mesh.practice.app.base.usecase.UseCaseError
import jp.glory.chaos.mesh.practice.app.base.usecase.UseCaseNotFoundError
import jp.glory.chaos.mesh.practice.app.base.usecase.toUseCaseError
import jp.glory.chaos.mesh.practice.app.product.domain.model.Member
import jp.glory.chaos.mesh.practice.app.product.domain.model.MemberID
import jp.glory.chaos.mesh.practice.app.product.domain.repository.MemberRepository
import java.time.LocalDate

@UseCase
class FindMemberUseCase(
    private val repository: MemberRepository
) {
    fun findById(id: String): Result<MemberSearchResult, UseCaseError> =
        repository.findById(MemberID(id))
            .mapBoth (
                success = {
                    if (it == null) {
                        createNotFound(id)
                    } else {
                        Ok(toResult(it))
                    }
                },
                failure = { Err(toUseCaseError(it)) }
            )

    fun findAll(): Result<MemberSearchResults, UseCaseError> =
        repository.findAll()
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }

    private fun toResults(members: List<Member>): MemberSearchResults =
        MemberSearchResults(members.map { MemberSearchResult(it) })

    private fun toResult(member: Member): MemberSearchResult =
        MemberSearchResult(member)

    private fun createNotFound(id: String): Err<UseCaseNotFoundError> =
        Err(
            UseCaseNotFoundError(
                resourceName = UseCaseNotFoundError.ResourceName.Product,
                idValue = id
            )
        )
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
