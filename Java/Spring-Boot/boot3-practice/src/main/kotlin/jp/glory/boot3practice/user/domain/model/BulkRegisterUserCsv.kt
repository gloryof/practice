package jp.glory.boot3practice.user.domain.model

import jp.glory.boot3practice.user.domain.event.InputPassword
import jp.glory.boot3practice.user.domain.event.UserRegisterEvent
import java.io.BufferedReader
import java.time.LocalDate

class BulkRegisterUserCsv(
    private val datasource: BufferedReader
) {
    fun loadUser(): LoadResult {
        val line = datasource.readLine()
            ?: return LoadResult.notExist

        return parse(line)
            ?.let { LoadResult.create(it) }
            ?: LoadResult.error
    }

    private fun parse(line: String): UserRegisterEvent? {
        val separated = line.split(",")

        if (separated.size < 3) {
            return null
        }

        return runCatching {
            UserRegisterEvent(
                id = UserId.generate(),
                name = UserName(separated[0]),
                birthDay = BirthDay(LocalDate.parse(separated[1])),
                password = InputPassword(separated[2])
            )
        }
            .getOrNull()
    }
}

class LoadResult(
    val existUser: Boolean,
    val isError: Boolean,
    val registerEvent: UserRegisterEvent?
) {
    companion object {
        val notExist = LoadResult(
            existUser = false,
            isError = false,
            registerEvent = null
        )
        val error = LoadResult(
            existUser = false,
            isError = true,
            registerEvent = null
        )

        fun create(event: UserRegisterEvent): LoadResult =
            LoadResult(
                existUser = true,
                isError = false,
                registerEvent = event
            )
    }
}