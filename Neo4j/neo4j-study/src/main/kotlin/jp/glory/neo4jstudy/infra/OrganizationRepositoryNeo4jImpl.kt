package jp.glory.neo4jstudy.infra

import jp.glory.neo4jstudy.domain.event.JoinToPostEvent
import jp.glory.neo4jstudy.domain.event.LeaveFromPostEvent
import jp.glory.neo4jstudy.domain.model.Organization
import jp.glory.neo4jstudy.domain.repository.OrganizationRepository
import jp.glory.neo4jstudy.externals.neo4j.dao.PostDao
import org.springframework.stereotype.Repository

/**
 * [OrganizationRepository]のNeo4j実装.
 */
@Repository
class OrganizationRepositoryNeo4jImpl(private val postDao: PostDao) : OrganizationRepository {

    override fun findAllOrganizations(): List<Organization> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}