package jp.glory.chaos.mesh.practice.app.product.adaptor.store

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.chaos.mesh.practice.app.base.domain.DomainUnknownError
import jp.glory.chaos.mesh.practice.app.product.domain.model.*
import jp.glory.chaos.mesh.practice.app.product.domain.repository.MemberRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
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

    override fun findAll(): Result<List<Member>, DomainUnknownError> {
        return members
            .map { it.value }
            .let { Ok(it) }
    }
}
