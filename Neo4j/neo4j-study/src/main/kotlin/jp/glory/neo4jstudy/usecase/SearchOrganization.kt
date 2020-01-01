package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.employee.model.Employee
import jp.glory.neo4jstudy.domain.organization.model.Organization
import jp.glory.neo4jstudy.domain.organization.repository.OrganizationRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase

/**
 * 組織を検索する.
 *
 * @param repository 組織リポジトリ
 */
@Usecase
class SearchOrganization(private val repository: OrganizationRepository) {

    /**
     * 全ての組織を検索する.
     *
     * @return 組織のリスト
     */
    fun searchAll(): OrganizationSearchResult {

        val results =  repository.findAllOrganizations()
            .map { OrganizationSearchDetail(it) }

        return OrganizationSearchResult(results)
    }

    /**
     * 組織の検索結果.
     *
     * @param results 検索詳細のリスト
     */
    data class OrganizationSearchResult(val results: List<OrganizationSearchDetail>)

    /**
     * 組織の検索結果詳細.
     *
     * @param organization 組織
     */
    class OrganizationSearchDetail(private val organization: Organization) {

        /**
         * 部署ID.
         */
        val postId: Long = organization.post.postId.value

        /**
         * 部署名.
         */
        val name: String = organization.post.name

        /**
         * 子部署.
         */
        val children: List<OrganizationSearchDetail> = organization.children.map { OrganizationSearchDetail(it) }

        /**
         * 所属従業員.
         */
        val belongedEmployees: List<BelongedEmployeeDetail> =
                organization.belongedEmployees.employees.map { BelongedEmployeeDetail(it) }
    }

    /**
     * 所属従業員詳細.
     *
     * @param employee 従業員
     */
    class BelongedEmployeeDetail(private val employee: Employee) {

        /**
         * 従業員ID.
         */
        val employeeId: Long = employee.employeeId.value

        /**
         * 姓.
         */
        val lastName: String = employee.lastName

        /**
         * 名.
         */
        val firstName: String = employee.firstName

        /**
         * 年齢.
         */
        val age: Int = employee.age
    }
}