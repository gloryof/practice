package jp.glory.practice.axon.user.infra.axon.query

import jp.glory.practice.axon.user.domain.model.User
import jp.glory.practice.axon.user.domain.query.FindUserQuery
import jp.glory.practice.axon.user.domain.query.FindUserResult
import jp.glory.practice.axon.user.domain.repository.UserRepository
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class FindUserQueryHandler(
    private val repository: UserRepository
) {
    @QueryHandler
    fun handle(query: FindUserQuery): FindUserResult? =
        repository.findById(query.userId)
            ?.let { toResult(it) }

    private fun toResult(user: User): FindUserResult =
        FindUserResult(
            userId = user.userId,
            name = user.name,
            address = user.address,
            giftPoint = user.giftPoint
        )
}