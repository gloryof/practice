package jp.glory.grpc.practice.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.grpc.practice.base.domain.DomainUnknownError
import jp.glory.grpc.practice.app.product.domain.model.Member
import jp.glory.grpc.practice.app.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Result<Member?, DomainUnknownError>
    fun findByIds(ids: List<MemberID>): Result<List<Member>, DomainUnknownError>
}
