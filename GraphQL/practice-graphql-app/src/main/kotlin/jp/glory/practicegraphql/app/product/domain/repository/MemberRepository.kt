package jp.glory.practicegraphql.app.product.domain.repository

import com.github.michaelbull.result.Result
import jp.glory.practicegraphql.app.base.domain.DomainUnknownError
import jp.glory.practicegraphql.app.product.domain.model.Member
import jp.glory.practicegraphql.app.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Result<Member?, DomainUnknownError>
    fun findByIds(ids: List<MemberID>): Result<List<Member>, DomainUnknownError>
}