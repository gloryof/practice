package jp.glory.ci.cd.practice.app.auth.infra

import jp.glory.ci.cd.practice.app.auth.domain.model.TokenGenerator
import jp.glory.ci.cd.practice.app.auth.domain.model.TokenValue
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenGeneratorImpl : TokenGenerator {
    override fun generate(): TokenValue =
        TokenValue(UUID.randomUUID().toString())
}