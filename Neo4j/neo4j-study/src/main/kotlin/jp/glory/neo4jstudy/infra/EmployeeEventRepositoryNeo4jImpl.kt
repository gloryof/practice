package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeEventRepository
import jp.glory.neo4jstudy.domain.post.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.post.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.externals.neo4j.dao.EmployeeHistoryDao
import jp.glory.neo4jstudy.externals.neo4j.dao.IdDao
import jp.glory.neo4jstudy.externals.neo4j.dao.PostDao
import jp.glory.neo4jstudy.externals.neo4j.node.PostNode
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Repository
class EmployeeEventRepositoryNeo4jImpl(
    private val historyDao: EmployeeHistoryDao,
    private val postDao: PostDao,
    private val idDao: IdDao) : EmployeeEventRepository {

    override fun saveEntry(employeeId: EmployeeId, event: EntryEmployeeEvent) {

        val post: PostNode = postDao.findByPostId(event.postId.value)
        val historyId: Long = generateNextId()

        historyDao.saveEntryHistory(
            historyId = historyId,
            entryAt = formatDate(event.entryAt),
            postId = event.postId.value,
            postName =  post.name
        )

        historyDao.relationHistoryToEmployee(
            employeeId = employeeId.value,
            historyId = historyId)
    }

    override fun saveRetire(event: RetireEmployeeEvent) {

        val historyId: Long = generateNextId()

        historyDao.saveRetireHistory(
            historyId = historyId,
            retireAt = formatDate(event.retireAt)
        )

        historyDao.relationHistoryToEmployee(
            employeeId = event.employeeId.value,
            historyId = historyId)
    }

    override fun saveJoining(event: JoinToPostEvent) {

        val post: PostNode = postDao.findByPostId(event.postId.value)
        val historyId: Long = generateNextId()

        historyDao.saveJoinHistory(
            historyId = historyId,
            joinAt = formatDate(event.joinAt),
            postId =  event.postId.value,
            postName = post.name
        )

        historyDao.relationHistoryToEmployee(
            employeeId = event.employeeId.value,
            historyId = historyId)
    }

    override fun saveLeaving(event: LeaveFromPostEvent) {

        val post: PostNode = postDao.findByPostId(event.postId.value)
        val historyId: Long = generateNextId()

        historyDao.saveLeaveHistory(
            historyId = historyId,
            leaveAt = formatDate(event.leaveAt),
            postId =  event.postId.value,
            postName = post.name
        )

        historyDao.relationHistoryToEmployee(
            employeeId = event.employeeId.value,
            historyId = historyId)
    }

    /**
     * 次の履歴IDを取得する.
     *
     * @return 履歴ID
     */
    private fun generateNextId(): Long {
        return idDao.calculateNextHistoryId().value
    }

    private fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}