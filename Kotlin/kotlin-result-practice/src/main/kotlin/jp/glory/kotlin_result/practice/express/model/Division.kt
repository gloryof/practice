package jp.glory.kotlin_result.practice.express.model

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class Division(
    private val value1: Int,
    private val value2: Int
) : Express {
    override fun execute(): Result<Int, ErrorInfo> {
        if (value1 == 0 || value2 == 0) {
            return Err(ErrorInfo.DIV_BY_ZERO)
        }

        return Ok(value1 / value2)
    }
}