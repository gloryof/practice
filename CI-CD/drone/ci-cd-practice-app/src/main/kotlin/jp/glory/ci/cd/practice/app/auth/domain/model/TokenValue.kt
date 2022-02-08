package jp.glory.ci.cd.practice.app.auth.domain.model

@JvmInline
value class TokenValue(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

interface TokenGenerator {
    fun generate(): TokenValue
}