package jp.glory.neo4jstudy.domain.organization.repository

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

    /**
     * 部署への所属を保存する.
     *
     * @param event 保存イベント
     */
    fun saveJoining(event: JoinToPostEvent)

    /**
     * 部署から外れた内容を保存する.
     *
     * @param event イベント
     */
    fun saveLeaving(event: LeaveFromPostEvent)
}