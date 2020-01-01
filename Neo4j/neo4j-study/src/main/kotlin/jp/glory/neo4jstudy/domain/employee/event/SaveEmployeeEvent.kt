package jp.glory.neo4jstudy.domain.employee.event

import jp.glory.neo4jstudy.domain.employee.model.EmployeeId

/**
 * 従業員保存イベント.
 *
 * @param employeeId 従業員ID。設定されていない場合は新規登録として扱われる。
 * @param lastName 姓
 * @param firstName 名
 * @param age 年齢
 */
class SaveEmployeeEvent(
    val employeeId: EmployeeId? = null,
    val lastName: String,
    val firstName: String,
    val age:Int
) {

    /**
     * 新規登録かを判定する.
     * @return 新規登録の場合：true、更新の場合：false
     */
    fun isRegister(): Boolean = (employeeId == null)
}