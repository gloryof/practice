package jp.glory.boot3practice.user.use_case

import jp.glory.boot3practice.base.use_case.UseCase
import jp.glory.boot3practice.user.domain.model.BulkRegisterUserCsv
import jp.glory.boot3practice.user.domain.model.LoadResult
import jp.glory.boot3practice.user.domain.repository.UserRegisterEventRepository
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@UseCase
class BulkRegisterUserUseCase(
    private val repository: UserRegisterEventRepository
) {
    class Input(
        val inputStream: InputStream
    )
    inner class Output(
        val count: Int
    )
    fun register(input: Input): Output =
        input.inputStream
            .use { stream ->
                BufferedReader(InputStreamReader(stream))
                    .use { executeBulkRegister(it) }
            }
            .let { Output(it) }

    private fun executeBulkRegister(
        reader: BufferedReader
    ): Int {
        var count = 0
        BulkRegisterUserCsv(reader)
            .let {
                var result: LoadResult
                do {
                    result = it.loadUser()

                    result.registerEvent
                        ?.let {
                            repository.save(it)
                            count++
                        }

                } while (result.existUser)
            }

        return count
    }
}