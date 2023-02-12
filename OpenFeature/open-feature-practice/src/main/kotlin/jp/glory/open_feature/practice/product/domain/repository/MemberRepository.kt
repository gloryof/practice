package jp.glory.open_feature.practice.product.domain.repository

import jp.glory.open_feature.practice.product.domain.model.Member
import jp.glory.open_feature.practice.product.domain.model.MemberID

interface MemberRepository {
    fun findById(id: MemberID): Member?
    fun findAll(): List<Member>
}