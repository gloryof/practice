package jp.glory.open_feature.practice.product.adaptor.store

import jp.glory.open_feature.practice.product.domain.model.*
import jp.glory.open_feature.practice.product.domain.repository.MemberRepository
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
    ): Member? = members[id.value]

    override fun findAll(): List<Member> =
        members.values.toList()

}