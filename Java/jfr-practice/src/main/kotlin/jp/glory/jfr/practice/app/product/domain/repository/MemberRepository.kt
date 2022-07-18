package jp.glory.jfr.practice.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.jfr.practice.app.base.domain.DomainUnknownError
import jp.glory.jfr.practice.app.product.domain.model.Member
import jp.glory.jfr.practice.app.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Result<Member?, DomainUnknownError>
    fun findAll(): Result<List<Member>, DomainUnknownError>
}
