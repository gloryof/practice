package jp.glory.neo4jstudy.domain.repository

import jp.glory.neo4jstudy.domain.event.DeleteEmployeeEvent
import jp.glory.neo4jstudy.domain.event.SaveEmployeeEvent
import jp.glory.neo4jstudy.domain.model.EmployeeId

/**
 * 従業員のリポジトリ.
 */
interface EmployeeRepository {

    /**
     * 従業員を保存する.
     *
     * @param event 保存イベント
     * @return 保存された従業員のIDを返す
     */
    fun save(event: SaveEmployeeEvent): EmployeeId

    /**
     * 従業員を削除する.
     *
     * @param event 削除イベント
     */
    fun delete(event: DeleteEmployeeEvent)
}