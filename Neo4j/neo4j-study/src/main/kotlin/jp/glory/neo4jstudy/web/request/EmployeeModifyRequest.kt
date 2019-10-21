package jp.glory.neo4jstudy.web.request

/**
 * 従業員変更リクエスト.
 *
 * @param lastName 姓
 * @param firstName 名前
 * @param age 年齢
 */
data class EmployeeModifyRequest(val lastName: String, val firstName: String, val age:Int)