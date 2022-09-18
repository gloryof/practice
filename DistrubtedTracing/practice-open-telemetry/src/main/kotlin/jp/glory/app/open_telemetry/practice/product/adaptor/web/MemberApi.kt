package jp.glory.app.open_telemetry.practice.product.adaptor.web

import com.github.michaelbull.result.*
import jp.glory.app.open_telemetry.practice.base.adaptor.web.error.WebError
import jp.glory.app.open_telemetry.practice.base.adaptor.web.error.WebErrorHelper
import jp.glory.app.open_telemetry.practice.product.usecase.FindMemberUseCase
import jp.glory.app.open_telemetry.practice.product.usecase.MemberSearchResult
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class MemberApi(
    private val findMemberUseCase: FindMemberUseCase,
) {
    fun findAll(): Result<MembersResponse, WebError> =
        transaction {
            findMemberUseCase.findAll()
                .map {
                    MembersResponse(
                        members = it.results.map { result -> toMemberResponse(result) }
                    )
                }
                .mapBoth(
                    success = { Ok(it) },
                    failure = { Err(WebErrorHelper.create(it)) }
                )
        }

    fun findById(
        id: String
    ): Result<MemberResponse, WebError> =
        transaction {
            findMemberUseCase.findById(id)
                .map { toMemberResponse(it) }
                .mapBoth(
                    success = { Ok(it) },
                    failure = { Err(WebErrorHelper.create(it)) }
                )
        }

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
