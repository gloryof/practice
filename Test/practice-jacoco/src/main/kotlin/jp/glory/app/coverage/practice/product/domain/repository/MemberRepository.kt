package jp.glory.app.coverage.practice.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.app.coverage.practice.base.domain.DomainUnknownError
import jp.glory.app.coverage.practice.product.domain.model.Member
import jp.glory.app.coverage.practice.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Result<Member?, DomainUnknownError>
    fun findAll(): Result<List<Member>, DomainUnknownError>
}
