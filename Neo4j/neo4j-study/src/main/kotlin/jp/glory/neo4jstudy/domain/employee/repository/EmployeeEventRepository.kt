package jp.glory.neo4jstudy.domain.employee.repository

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.post.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.post.event.LeaveFromPostEvent

/**
 * 従業員イベントリポジトリ.
 */
interface EmployeeEventRepository {

    /**
     * 入社イベントを履歴として記録する.
     *
     * @param employeeId 従業員ID
     * @param event 入社イベント
     */
    fun saveEntry(employeeId: EmployeeId, event:EntryEmployeeEvent)

    /**
     * 退職イベントを履歴として記録する.
     *
     * @param event 退職イベント
     */
    fun saveRetire(event:RetireEmployeeEvent)

    /**
     * 所属イベントを履歴として記録する.
     *
     * @param event 所属イベント
     */
    fun saveJoining(event:JoinToPostEvent)

    /**
     * 部署から外れるイベントを履歴として記録する.
     *
     * @param event 部署から外れるイベント
     */
    fun saveLeaving(event: LeaveFromPostEvent)
}