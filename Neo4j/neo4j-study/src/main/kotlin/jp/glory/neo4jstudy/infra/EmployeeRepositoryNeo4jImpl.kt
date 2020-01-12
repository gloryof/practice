package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.UpdateEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.Employee
import jp.glory.neo4jstudy.domain.employee.model.EmployeeDetail
import jp.glory.neo4jstudy.domain.employee.model.EmployeeHistory
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.model.EntryHistory
import jp.glory.neo4jstudy.domain.employee.model.JoinToPostHistory
import jp.glory.neo4jstudy.domain.employee.model.LeaveFromPostHistory
import jp.glory.neo4jstudy.domain.employee.model.RetireHistory
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.domain.post.model.Post
import jp.glory.neo4jstudy.domain.post.model.PostId
import jp.glory.neo4jstudy.externals.neo4j.dao.EmployeeDao
import jp.glory.neo4jstudy.externals.neo4j.dao.EmployeeHistoryDao
import jp.glory.neo4jstudy.externals.neo4j.dao.IdDao
import jp.glory.neo4jstudy.externals.neo4j.dao.PostDao
import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeHistoryNode
import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeNode
import jp.glory.neo4jstudy.externals.neo4j.node.EntryHistoryNode
import jp.glory.neo4jstudy.externals.neo4j.node.JoinHistoryNode
import jp.glory.neo4jstudy.externals.neo4j.node.LeaveHistoryNode
import jp.glory.neo4jstudy.externals.neo4j.node.RetireHistoryNode
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * [EmployeeRepository]のNeo4j実装.
 *
 * @param empDao 従業員DAO
 * @param idDao IDのDAO
 * @param postDao 部署DAO
 */
@Repository
class EmployeeRepositoryNeo4jImpl(
    private val empDao: EmployeeDao,
    private val idDao: IdDao,
    private val postDao: PostDao,
    private val historyDao: EmployeeHistoryDao) : EmployeeRepository {

    /**
     * IDをキーとして従業員詳細を取得する.
     *
     * @param id 従業員ID
     * @return 従業員詳細
     */
    override fun findDetailById(id: EmployeeId): EmployeeDetail {

        val node: EmployeeNode = empDao.findByEmployeeId(id.value)

        val employee = Employee(
            employeeId = EmployeeId(node.employeeId),
            lastName = node.lastName,
            firstName = node.firstName,
            age = node.age
        )

        val historyNodes: List<EmployeeHistoryNode> = historyDao.findByEmployeeId(id.value)
        val histories: List<EmployeeHistory> = historyNodes.map { convertToHistory(it) }

        return EmployeeDetail(
            employee = employee,
            histories = histories.sortedBy { it.happenedAt }
        )
    }

    override fun update(event: UpdateEmployeeEvent) {

        empDao.merge(
            employeeId =  event.employeeId.value,
            lastName = event.lastName,
            firstName = event.firstName,
            age = event.age
        )
    }

    override fun saveEntry(event: EntryEmployeeEvent): EmployeeId {

        val employeeId = generateNewId()

        empDao.merge(
            employeeId = employeeId.value,
            lastName = event.lastName,
            firstName = event.firstName,
            age = event.age
        )

        postDao.addEmployeeRelation(
            postId = event.postId.value,
            employeeId = employeeId.value
        )

        return employeeId
    }

    override fun saveRetire(event: RetireEmployeeEvent) {

        empDao.updateToRetire(event.employeeId.value)
        empDao.deleteEmployeeJoining(event.employeeId.value)
    }

    /**
     * 新しい従業員IDを発行する.
     *
     * @return 従業員ID
     */
    private fun generateNewId(): EmployeeId {
        return EmployeeId(idDao.calculateNextEmployeeId().value)
    }

    /**
     * 履歴ノードから履歴エンティティに変換する.
     *
     * @param node 履歴ノード
     * @return 履歴エンティティ
     */
    private fun convertToHistory(node: EmployeeHistoryNode): EmployeeHistory {

        return when(node) {
            is EntryHistoryNode -> EntryHistory(
                entryAt = convertToDate(node.entryAt),
                post = Post(
                    postId = PostId(node.postId),
                    name = node.postName
                )
            )
            is JoinHistoryNode -> JoinToPostHistory(
                joinAt = convertToDate(node.joinAt),
                post = Post(
                    postId = PostId(node.postId),
                    name = node.postName
                )
            )
            is LeaveHistoryNode -> LeaveFromPostHistory(
                leaveAt = convertToDate(node.leaveAt),
                post = Post(
                    postId = PostId(node.postId),
                    name = node.postName
                )
            )
            is RetireHistoryNode -> RetireHistory(
                retireAt = convertToDate(node.retireAt)
            )
        }
    }

    /**
     * Neo4jの日付表現を日付に変換する.
     *
     * @value value 日付表現文字列
     * @return 日付
     */
    private fun convertToDate(value: String): LocalDate {

        return LocalDate.parse(
            value,
            DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}