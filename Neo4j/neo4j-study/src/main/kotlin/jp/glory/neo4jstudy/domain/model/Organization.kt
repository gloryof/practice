package jp.glory.neo4jstudy.domain.model

/**
 * 組織.
 *
 * @param post 部署
 * @param children 子部署
 * @param belongedEmployees 所属従業員
 */
class Organization(
    val post: Post,
    val children:List<Organization>,
    val belongedEmployees: BelongedEmployees)