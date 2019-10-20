package jp.glory.neo4jstudy.domain.model

/**
 * 従業員.
 *
 * @param employeeId 従業員ID
 * @param lastName 姓
 * @param firstName 名
 * @param age 年齢
 */
class Employee(val employeeId:EmployeeId, val lastName: String, val firstName: String, val age:Int)