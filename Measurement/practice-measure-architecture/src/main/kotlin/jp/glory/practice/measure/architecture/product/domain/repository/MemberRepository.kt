package jp.glory.practice.measure.architecture.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.practice.measure.architecture.base.domain.DomainUnknownError
import jp.glory.practice.measure.architecture.product.domain.model.Member
import jp.glory.practice.measure.architecture.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Result<Member?, DomainUnknownError>
    fun findByIds(ids: List<MemberID>): Result<List<Member>, DomainUnknownError>
}