package jp.glory.ci.cd.practice.app.rest_assured.util

object EnvironmentExtractor {
    fun getTargetUrl(): String =
        getEnv("TARGET_URL", "http://localhost")

    fun getTargetPort(): Int =
        getEnv("TARGET_PORT", "8080")
            .toInt()

    fun getIdPassword(): IdPassword = IdPassword(
        userId = getEnv("USER_ID", "test-system-user-id"),
        password = getEnv("PASSWORD", "test-system-password"),
    )

    fun getTestUserId(): String = getEnv("USER_ID", "test-system-user-id")

    fun getUpdateUserId(): String = getEnv("UPDATE_USER_ID", "test-for-update-user-id")

    fun getTestFileLocation(): String =
        getEnv("TEST_FILE_LOCATION",  "/tmp/test-files")

    private fun getEnv(
        key: String,
        default: String
    ): String = System.getenv(key) ?: default
}

data class IdPassword(
    val userId: String,
    val password: String
)