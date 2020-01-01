package jp.glory.neo4jstudy.domain.organization.model

import jp.glory.neo4jstudy.domain.employee.model.Employee

/**
 * 従業員所属.
 *
 * @param employee 従業員
 * @param belongedPosts 所属部曽
 */
class EmployeeBelong(val employee: Employee, val belongedPosts: BelongedPosts)