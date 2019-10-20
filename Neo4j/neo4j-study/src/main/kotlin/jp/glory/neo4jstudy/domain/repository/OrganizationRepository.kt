package jp.glory.neo4jstudy.domain.repository

import jp.glory.neo4jstudy.domain.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.model.Organization

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