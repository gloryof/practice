package jp.glory.neo4jstudy.web.response

import jp.glory.neo4jstudy.usecase.SearchEmployee
import java.time.LocalDate

/**
 * 従業員履歴レスポンス.
 *
 * @param result 従業員履歴
 */
class EmployeeHistoryDetailResponse (private val result: SearchEmployee.EmployeeHistoryResult) {

    /**
     * 履歴のタイプ.
     */
    val type: String = result.type.name

    /**
     * 発生日.
     */
    val happenedAt: LocalDate = result.happenedAt

    /**
     * 部署名.
     */
    val postName: String = result.postName
}