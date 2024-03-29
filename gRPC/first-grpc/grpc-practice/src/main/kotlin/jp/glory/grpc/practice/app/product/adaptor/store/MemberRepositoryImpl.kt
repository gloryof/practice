package jp.glory.grpc.practice.app.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.grpc.practice.app.product.domain.model.*
import jp.glory.grpc.practice.base.domain.DomainUnknownError
import jp.glory.grpc.practice.app.product.domain.repository.MemberRepository
import java.time.LocalDate

class MemberRepositoryImpl : MemberRepository {
    private val members: MutableMap<String, Member> = mutableMapOf()

    init {
        repeat(10) {
            val id = "member-id-$it"
            val member = Member(
                id = MemberID("member-id-$it"),
                name = MemberName(
                    givenName = GivenName("member-given-name-$it"),
                    familyName = FamilyName("member-family-name-$it"),
                ),
                birthDay = LocalDate.now()
            )
            members[id] = member
        }
    }

    override fun findById(
        id: MemberID
    ): Result<Member?, DomainUnknownError> = Ok(members[id.value])

    override fun findByIds(ids: List<MemberID>): Result<List<Member>, DomainUnknownError> {
        return members
            .filter { (key, _) -> ids.contains(MemberID(key)) }
            .map { it.value }
            .let { Ok(it) }
    }
}
