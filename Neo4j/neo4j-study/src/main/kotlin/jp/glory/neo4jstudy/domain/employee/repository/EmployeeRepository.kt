package jp.glory.neo4jstudy.domain.employee.repository

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.UpdateEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeDetail
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId

/**
 * 従業員のリポジトリ.
 */
interface EmployeeRepository {

    /**
     * IDをキーとして従業員詳細を取得する.
     *
     * @param id 従業員ID
     * @return 従業員詳細
     */
    fun findDetailById(id: EmployeeId): EmployeeDetail

    /**
     * 従業員を更新する.
     *
     * @param event 保存イベント
     */
    fun update(event: UpdateEmployeeEvent)

    /**
     * 従業員の入社イベントを保存する.
     *
     * @param event 入社イベント
     */
    fun saveEntry(event: EntryEmployeeEvent)

    /**
     * 従業員の退職イベントを保存する.
     *
     * @param event 退職イベント
     */
    fun saveRetire(event: RetireEmployeeEvent)
}