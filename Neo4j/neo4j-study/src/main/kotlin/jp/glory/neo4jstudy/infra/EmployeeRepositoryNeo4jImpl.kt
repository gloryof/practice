package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.employee.event.EntryEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.RetireEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.UpdateEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.Employee
import jp.glory.neo4jstudy.domain.employee.model.EmployeeDetail
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.externals.neo4j.dao.EmployeeDao
import jp.glory.neo4jstudy.externals.neo4j.dao.IdDao
import jp.glory.neo4jstudy.externals.neo4j.dao.PostDao
import jp.glory.neo4jstudy.externals.neo4j.node.EmployeeNode
import org.springframework.stereotype.Repository

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
    private val postDao: PostDao) : EmployeeRepository {

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

        return EmployeeDetail(
            employee = employee,
            histories = listOf()
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
}