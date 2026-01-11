package jp.glory.practice.boot.app.user.query.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType
import jp.glory.practice.boot.app.user.data.UserDao
import jp.glory.practice.boot.app.user.data.UserRecord
import java.time.LocalDate

class GetUser(
    private val dao: UserDao
) {
    fun getById(userId: String): Result<Output, UsecaseErrors> =
        dao.findById(userId)
            ?.let { toOutput(it) }
            ?.let { Ok(it) }
            ?: Err(createError())

    private fun toOutput(record: UserRecord): Output = Output(
        userId = record.userId,
        userName = record.userName,
        birthday = record.birthday
    )

    private fun createError(): UsecaseErrors = UsecaseErrors(
        specErrors = listOf(UsecaseSpecErrorType.DATA_IS_NOT_FOUND)
    )

    class Output(
        val userId: String,
        val userName: String,
        val birthday: LocalDate,
    )
}
