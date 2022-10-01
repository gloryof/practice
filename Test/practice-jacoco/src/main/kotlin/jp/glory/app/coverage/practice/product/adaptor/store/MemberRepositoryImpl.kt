package jp.glory.app.coverage.practice.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.app.coverage.practice.base.domain.DomainUnknownError
import jp.glory.app.coverage.practice.product.domain.model.*
import jp.glory.app.coverage.practice.product.domain.repository.MemberRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class MemberRepositoryImpl : MemberRepository {
    override fun findById(
        id: MemberID
    ): Result<Member?, DomainUnknownError> =
        MemberTable
            .select { MemberTable.id eq id.value }
            .firstOrNull()
            ?.let { toDomainModel(it) }
            .let { Ok(it) }

    override fun findAll(): Result<List<Member>, DomainUnknownError> =
        MemberTable.selectAll()
            .map { toDomainModel(it) }
            .let { Ok(it) }

    private fun toDomainModel(row: ResultRow): Member =
        Member(
            id = MemberID(row[MemberTable.id]),
            name = MemberName(
                givenName = GivenName(row[MemberTable.givenName]),
                familyName = FamilyName(row[MemberTable.familyName]),
            ),
            birthDay = row[MemberTable.birthDay]
        )

}
