package jp.glory.practice.graphql.document.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.practice.graphql.document.base.domain.DomainUnknownError
import jp.glory.practice.graphql.document.product.domain.model.Member
import jp.glory.practice.graphql.document.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Result<Member?, DomainUnknownError>
    fun findByIds(ids: List<MemberID>): Result<List<Member>, DomainUnknownError>
}