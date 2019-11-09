package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.event.AddChildPostEvent
import jp.glory.neo4jstudy.domain.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.model.EmployeeId
import jp.glory.neo4jstudy.domain.model.PostId
import jp.glory.neo4jstudy.domain.repository.OrganizationRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase

/**
 * 組織の変更.
 *
 * @param repository 組織リポジトリ
 */
@Usecase
class ModifyOrganization(private val repository: OrganizationRepository) {

    /**
     * 従業員を部署に所属させる.
     *
     * @param postId 部署ID
     * @param employeeId 従業員ID
     */
    fun joinEmployee(postId: Long, employeeId: Long) {

        val event = JoinToPostEvent(
            postId = PostId(postId),
            employeeId = EmployeeId(employeeId)
        )

        repository.saveJoining(event)
    }
    /**
     * 従業員を部署から外す
     *
     * @param postId 部署ID
     * @param employeeId 従業員ID
     */
    fun leaveEmployee(postId: Long, employeeId: Long) {

        val event = LeaveFromPostEvent(
            postId = PostId(postId),
            employeeId = EmployeeId(employeeId)
        )

        repository.saveLeaving(event)
    }
}