package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.employee.event.UpdateEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase

/***
 * 従業員の編集.
 *
 * @param repository 従業員リポジトリ
 */
@Usecase
class ModifyEmployee(
    private val repository: EmployeeRepository) {

    /**
     * 従業員の更新を行う.
     *
     * @param info 登録情報
     */
    fun update(info: UpdateInfo) {

        val event = UpdateEmployeeEvent(
            employeeId = EmployeeId(info.employeeId),
            lastName = info.lastName,
            firstName = info.firstName,
            age = info.age
        )

        repository.update(event)
    }

    /**
     * 従業員の更新情報.
     *
     * @param employeeId 従業員ID
     * @param lastName 姓
     * @param firstName 名
     * @param age 年齢
     */
    data class UpdateInfo(
        val employeeId: Long,
        val lastName: String,
        val firstName: String,
        val age:Int)
}