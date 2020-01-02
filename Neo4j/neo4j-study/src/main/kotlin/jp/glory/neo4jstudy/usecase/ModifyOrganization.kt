package jp.glory.neo4jstudy.usecase

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.post.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.post.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeEventRepository
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.domain.post.model.PostId
import jp.glory.neo4jstudy.domain.post.event.AddChildPostEvent
import jp.glory.neo4jstudy.domain.post.repository.PostRepository
import jp.glory.neo4jstudy.usecase.annotation.Usecase
import java.time.LocalDate

/**
 * 組織の変更.
 *
 * @param postRepository 部署リポジトリ
 * @param employeeRepository 従業員リポジトリ
 * @param eventRepository 従業員イベントリポジトリ
 */
@Usecase
class ModifyOrganization(
    private val postRepository: PostRepository,
    private val employeeRepository: EmployeeRepository,
    private val eventRepository: EmployeeEventRepository) {

    /**
     * 従業員を入社させる.
     */
    fun entryEmployee(info: EntryInfo) {

        val event = EntryEmployeeEvent(
            entryAt = info.entryAt,
            lastName = info.lastName,
            firstName = info.firstName,
            age = info.age,
            postId = PostId(info.postId)
        )

        employeeRepository.saveEntry(event)
        eventRepository.saveEntry(event)
    }

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

        postRepository.saveJoining(event)
        eventRepository.saveJoining(event)
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

        postRepository.saveLeaving(event)
        eventRepository.saveLeaving(event)
    }

    /**
     * 従業員を退職させる.
     *
     * @param info 退職情報
     */
    fun retireEmployee(info: RetireInfo) {

        val event = RetireEmployeeEvent(
            retireAt = info.retireAt,
            employeeId = EmployeeId(info.employeeId)
        )

        employeeRepository.saveRetire(event)
        eventRepository.saveRetire(event)
    }

    /**
     * 入社情報.
     *
     * @param entryAt 入社日
     * @param lastName 姓
     * @param firstName 名
     * @param age 年齢
     * @param postId 所属部署ID
     */
    data class EntryInfo(
        val entryAt: LocalDate,
        val lastName: String,
        val firstName: String,
        val age:Int,
        val postId: Long)

    /**
     * 退職情報.
     *
     * @param retireAt 退職日
     * @param employeeId 従業員ID
     */
    data class RetireInfo(
        val retireAt: LocalDate,
        val employeeId: Long
    )
}