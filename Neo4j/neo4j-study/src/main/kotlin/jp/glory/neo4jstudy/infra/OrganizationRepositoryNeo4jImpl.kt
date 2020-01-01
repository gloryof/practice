package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.post.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.post.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.employee.model.Employee
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.organization.model.Organization
import jp.glory.neo4jstudy.domain.post.model.Post
import jp.glory.neo4jstudy.domain.post.model.PostId
import jp.glory.neo4jstudy.domain.organization.repository.OrganizationRepository
import jp.glory.neo4jstudy.externals.neo4j.dao.EmployeeDao
import jp.glory.neo4jstudy.externals.neo4j.dao.PostDao
import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeNode
import jp.glory.neo4jstudy.externals.neo4j.node.PostNode
import jp.glory.neo4jstudy.externals.neo4j.result.WithParentIdResult
import org.springframework.stereotype.Repository

/**
 * [OrganizationRepository]のNeo4j実装.
 *
 * @param postDao 部署DAO
 * @param employeeDao 従業員DAO
 */
@Repository
class OrganizationRepositoryNeo4jImpl(
    private val postDao: PostDao,
    private val employeeDao: EmployeeDao) :
    OrganizationRepository {

    override fun findAllOrganizations(): List<Organization> {

        val organization: List<Organization> = findOrganizationsWithoutEmployee()

        joinEmployees(organization)

        return organization
    }


    override fun saveJoining(event: JoinToPostEvent) {

        postDao.addEmployeeRelation(
            postId = event.postId.value,
            employeeId = event.employeeId.value
        )
    }

    override fun saveLeaving(event: LeaveFromPostEvent) {

        postDao.deleteEmployeeRelation(
            postId = event.postId.value,
            employeeId = event.employeeId.value
        )
    }

    /**
     * 従業員を保持しない組織リストを取得する.
     *
     * @return 組織リスト
     */
    private fun findOrganizationsWithoutEmployee(): List<Organization> {

        val rootNodes: List<PostNode> = postDao.findRootNode()

        val organizations:List<Organization> = rootNodes
            .map { Post(PostId(it.postId), it.name) }
            .map { Organization(it) }
            .toList()

        organizations.forEach{belongChildren(it)}

        return organizations
    }

    /**
     * ルートの親部署に所属を追加する.
     *
     * @param root ルートの部署
     */
    private fun belongChildren(root: Organization) {

        postDao.findTreeIdByRootId(root.post.postId.value)
            .forEach{belongTo(root, it)}
    }

    /**
     * 対象組織に部署を追加する.
     *
     * @param organization 組織
     * @param targetPostId 対象部署ID
     */
    private fun belongTo(organization: Organization, targetPostId: Long) {

        val result: WithParentIdResult =
            postDao.findNodeWithParentId(targetPostId)

        val parentPostId = PostId(result.parentPostId)

        val postId = PostId(result.postId)
        val post = Post(postId = postId, name = result.name)

        organization.belongTo(parentPostId = parentPostId, post = post)
    }

    private fun joinEmployees(organizations: List<Organization>) {

        employeeDao.findAllEmployeesWithPost()
            .map { PostId(value = it.joinPostId) to convertEmployee(it.employee) }
            .forEach{
                organizations.any {it2 -> it2.joinTo(it.first, it.second)}
            }

    }

    /**
     * 従業員ノードから従業員に変換する.
     *
     * @param node 従業員ノード
     * @return 従業員
     */
    private fun convertEmployee(node: EmployeeNode): Employee {

        return Employee(
            employeeId = EmployeeId(value = node.employeeId),
            firstName = node.firstName,
            lastName = node.lastName,
            age = node.age
        )
    }

}