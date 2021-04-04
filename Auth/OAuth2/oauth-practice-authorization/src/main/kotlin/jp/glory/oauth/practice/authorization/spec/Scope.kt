package jp.glory.oauth.practice.authorization.spec

enum class Scope {
    READ,
    WRITE;

    companion object {
        fun codeFrom(value: String): Scope? =
            value.toUpperCase()
                .let {
                    values()
                        .first { scope -> it == scope.name }
                }
    }
}