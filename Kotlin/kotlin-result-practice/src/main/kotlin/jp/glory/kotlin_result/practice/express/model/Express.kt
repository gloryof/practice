package jp.glory.kotlin_result.practice.express.model

import com.github.michaelbull.result.Result

interface Express {
    fun execute(): Result<Int, ErrorInfo>
}

