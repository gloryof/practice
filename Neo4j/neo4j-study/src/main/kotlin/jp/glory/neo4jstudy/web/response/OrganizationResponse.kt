package jp.glory.neo4jstudy.web.response

import jp.glory.neo4jstudy.usecase.SearchOrganization

/**
 * 組織の詳細レスポンス.
 *
 * @param detail 組織
 */
data class OrganizationResponse(private val detail: SearchOrganization.OrganizationSearchDetail) {


    /**
     * 部署ID.
     */
    val postId: Long = detail.postId

    /**
     * 部署名.
     */
    val name: String = detail.name

    /**
     * 子部署.
     */
    val children: List<OrganizationResponse> = detail.children.map { OrganizationResponse(it) }

    /**
     * 所属従業員.
     */
    val belongedEmployees: List<BelongedEmployeeResponse> =
        detail.belongedEmployees.map { BelongedEmployeeResponse(it) }
}