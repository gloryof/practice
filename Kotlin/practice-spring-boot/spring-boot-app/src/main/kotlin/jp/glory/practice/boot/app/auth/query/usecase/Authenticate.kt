package jp.glory.practice.boot.app.auth.query.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.practice.boot.app.auth.data.TokenDao
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseErrors
import jp.glory.practice.boot.app.base.common.usecase.exception.UsecaseSpecErrorType

class Authenticate(
    private val tokenDao: TokenDao
) {

    fun authenticate(token: String): Result<Output, UsecaseErrors> =
        tokenDao.findByToken(token)
            ?.let { Ok(Output(it.userId)) }
            ?: Err(
                UsecaseErrors(
                    specErrors = listOf(UsecaseSpecErrorType.NOT_AUTHORIZED)
                )
            )

    class Output(
        val userId: String
    )
}
