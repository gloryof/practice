package jp.glory.neo4jstudy.externals.neo4j.node

import org.neo4j.ogm.annotation.NodeEntity

/**
 * 従業員ノード.
 *
 * @param employeeId 従業員ID
 * @param lastName 姓
 * @param firstName 名
 * @param age 年齢
 */
@NodeEntity(label = "Employee")
data class EmployeeNode(val employeeId:Long, val lastName: String, val firstName: String, val age:Int)