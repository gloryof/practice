package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.employee.event.DeleteEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.SaveEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase

/***
 * 従業員の編集.
 *
 * @param repository 従業員リポジトリ
 */
@Usecase
class ModifyEmployee(private val repository: EmployeeRepository) {

    /**
     * 従業員の登録を行う.
     *
     * @param info 登録情報
     */
    fun register(info: RegisterInfo): Long {

        val event = SaveEmployeeEvent(
            lastName = info.lastName,
            firstName = info.firstName,
            age = info.age
        )

        val id: EmployeeId = repository.save(event)

        return id.value
    }

    /**
     * 従業員の更新を行う.
     *
     * @param info 登録情報
     */
    fun update(info: UpdateInfo): Long {

        val event = SaveEmployeeEvent(
            employeeId = EmployeeId(info.employeeId),
            lastName = info.lastName,
            firstName = info.firstName,
            age = info.age
        )

        val id: EmployeeId = repository.save(event)

        return id.value
    }

    /**
     * 従業員の削除を行う.
     *
     * @param employeeId 従業員ID
     */
    fun delete(employeeId: Long) {

        val event = DeleteEmployeeEvent(
            employeeId = EmployeeId(employeeId)
        )

        repository.delete(event)
    }

    /**
     * 従業員の登録情報.
     *
     * @param lastName 姓
     * @param firstName 名
     * @param age 年齢
     */
    class RegisterInfo(val lastName: String, val firstName: String, val age:Int)

    /**
     * 従業員の更新情報.
     *
     * @param employeeId 従業員ID
     * @param lastName 姓
     * @param firstName 名
     * @param age 年齢
     */
    class UpdateInfo(val employeeId: Long, val lastName: String, val firstName: String, val age:Int)
}