package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.employee.event.DeleteEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.event.SaveEmployeeEvent
import jp.glory.neo4jstudy.domain.employee.model.EmployeeId
import jp.glory.neo4jstudy.domain.employee.repository.EmployeeRepository
import jp.glory.neo4jstudy.externals.neo4j.dao.EmployeeDao
import jp.glory.neo4jstudy.externals.neo4j.dao.IdDao
import org.springframework.stereotype.Repository

/**
 * [EmployeeRepository]のNeo4j実装.
 *
 * @param empDao 従業員DAO
 * @param idDao IDのDAO
 */
@Repository
class EmployeeRepositoryNeo4jImpl(
    private val empDao: EmployeeDao,
    private val idDao: IdDao) : EmployeeRepository {

    override fun save(event: SaveEmployeeEvent): EmployeeId {

        val id: EmployeeId = event.employeeId ?: generateNewId()

        empDao.merge(
            employeeId =  id.value,
            lastName = event.lastName,
            firstName = event.firstName,
            age = event.age
        )

        return id
    }

    override fun delete(event: DeleteEmployeeEvent) {

        empDao.deleteByEmployeeId(event.employeeId.value)
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