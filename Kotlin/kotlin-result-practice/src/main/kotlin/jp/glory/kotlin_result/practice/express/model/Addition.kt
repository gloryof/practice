package jp.glory.kotlin_result.practice.express.model

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class Addition(
    private val value1: Int,
    private val value2: Int
) : Express {
    override fun execute(): Result<Int, ErrorInfo> =
        Ok(value1 + value2)
}