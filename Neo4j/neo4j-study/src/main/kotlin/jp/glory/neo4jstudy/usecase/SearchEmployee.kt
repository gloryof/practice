package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.employee.model.Employee
import jp.glory.neo4jstudy.domain.employee.model.EmployeeDetail
import jp.glory.neo4jstudy.domain.employee.model.EmployeeHistory
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.model.EntryHistory
import jp.glory.neo4jstudy.domain.employee.model.JoinToPostHistory
import jp.glory.neo4jstudy.domain.employee.model.LeaveFromPostHistory
import jp.glory.neo4jstudy.domain.employee.model.RetireHistory
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase
import java.time.LocalDate

/**
 * 従業員を検索する.
 *
 * @param repository 従業員リポジトリ
 */
@Usecase
class SearchEmployee(private val repository: EmployeeRepository) {

    /**
     * 従業員IDをキーに従業員の検索を行う.
     *
     * @param employeeId 従業員ID
     * @return 従業員
     */
    fun findByEmployeeId(employeeId: Long): EmployeeDetailResult {

        val id = EmployeeId(employeeId)

        val employeeDetail: EmployeeDetail = repository.findDetailById(id)

        val employee: Employee = employeeDetail.employee

        return EmployeeDetailResult(
            employeeId = employee.employeeId.value,
            lastName = employee.lastName,
            firstName = employee.firstName,
            age = employee.age,
            histories = employeeDetail.histories.map { convertToHistoryResult(it) }
        )
    }

    /**
     * 従業員履歴を履歴の結果に変換する.
     *
     * @param history 従業員履歴
     * @return 履歴の結果
     */
    private fun convertToHistoryResult(
        history: EmployeeHistory): EmployeeHistoryResult {

        return when(history) {
            is EntryHistory -> EmployeeHistoryResult(
                type = EmployeeHistoryType.Entry,
                happenedAt = history.entryAt
            )
            is JoinToPostHistory -> EmployeeHistoryResult(
                type = EmployeeHistoryType.Join,
                happenedAt = history.joinAt,
                postName = history.post.name
            )
            is LeaveFromPostHistory -> EmployeeHistoryResult(
                type = EmployeeHistoryType.Leave,
                happenedAt = history.leaveAt,
                postName = history.post.name
            )
            is RetireHistory -> EmployeeHistoryResult(
                type = EmployeeHistoryType.Retire,
                happenedAt = history.retireAt
            )
        }

    }

    /**
     * 従業員詳細の結果.
     *
     * @param employeeId 従業員ID
     * @param lastName 姓
     * @param firstName 名
     * @param age 年齢
     * @param histories 履歴リスト
     */
    class EmployeeDetailResult(
        val employeeId: Long,
        val lastName: String,
        val firstName: String,
        val age:Int,
        val histories: List<EmployeeHistoryResult>
    )

    /**
     * 従業員
     */
    data class EmployeeHistoryResult(
        val type: EmployeeHistoryType,
        val happenedAt: LocalDate,
        val postName: String = ""
    )

    enum class EmployeeHistoryType {
        Entry,
        Join,
        Leave,
        Retire
    }
}
