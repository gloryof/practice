package jp.glory.ci.cd.practice.app.user.infra

import jp.glory.ci.cd.practice.app.user.domain.model.UserId
import jp.glory.ci.cd.practice.app.user.domain.model.UserIdGenerator
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserIdGeneratorImpl : UserIdGenerator {
    override fun generate(): UserId =
        UserId(UUID.randomUUID().toString())
}