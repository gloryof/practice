package jp.glory.boot3practice.e2e.filter

object EnvironmentExtractor {
    fun getTargetUrl(): String =
        getEnv("TARGET_URL", "http://localhost")

    fun getTargetPort(): Int =
        getEnv("TARGET_PORT", "8080")
            .toInt()

    fun getIdPassword(): IdPassword = IdPassword(
        userId = getEnv("USER_ID", "test-user-id"),
        password = getEnv("PASSWORD", "test-password"),
    )

    fun getTestUserId(): String = getEnv("USER_ID", "test-user-id")

    private fun getEnv(
        key: String,
        default: String
    ): String = System.getenv(key) ?: default
}

data class IdPassword(
    val userId: String,
    val password: String
)