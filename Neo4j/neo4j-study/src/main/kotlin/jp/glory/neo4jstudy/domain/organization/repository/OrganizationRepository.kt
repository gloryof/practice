package jp.glory.neo4jstudy.domain.organization.repository

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.post.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.post.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.organization.model.Organization

/**
 * [Organization]のリポジトリ.
 */
interface OrganizationRepository {

    /**
     * 全ての組織を検索する.
     *
     * @return 組織のリスト
     */
    fun findAllOrganizations(): List<Organization>
}