package jp.glory.app.open_telemetry.practice.product.usecase

import com.github.michaelbull.result.*
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseNotFoundError
import jp.glory.app.open_telemetry.practice.base.usecase.UseCaseTelemetry
import jp.glory.app.open_telemetry.practice.base.usecase.toUseCaseError
import jp.glory.app.open_telemetry.practice.product.domain.model.Member
import jp.glory.app.open_telemetry.practice.product.domain.model.MemberID
import jp.glory.app.open_telemetry.practice.product.domain.repository.MemberRepository
import java.time.LocalDate

class FindMemberUseCase(
    private val repository: MemberRepository,
    private val useCaseTelemetry: UseCaseTelemetry
) {
    fun findById(id: String): Result<MemberSearchResult, UseCaseError> {
        registerTelemetry("findById")
        return repository.findById(MemberID(id))
            .mapBoth(
                success = {
                    if (it == null) {
                        createNotFound(id)
                    } else {
                        Ok(toResult(it))
                    }
                },
                failure = { Err(toUseCaseError(it)) }
            )
    }

    fun findAll(): Result<MemberSearchResults, UseCaseError> {
        registerTelemetry("findAll")
        return repository.findAll()
            .map { toResults(it) }
            .mapError { toUseCaseError(it) }
    }

    private fun registerTelemetry(methodName: String) {
        useCaseTelemetry.registerUseCaseAttribute(
            useCaseName = "findMember",
            methodName = methodName
        )
    }

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
