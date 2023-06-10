package jp.glory.kotlin_result.practice.express.inspection

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.unwrap
import com.github.michaelbull.result.zip
import jp.glory.kotlin_result.practice.express.model.Addition
import jp.glory.kotlin_result.practice.express.model.Division
import jp.glory.kotlin_result.practice.express.model.ErrorInfo
import jp.glory.kotlin_result.practice.express.model.Subtraction

class ZipInspection {
    /**
     * (5+4)/(1+2) = 3
     */
    fun success(): Result<Int, ErrorInfo> =
        zip(
            { Addition(5, 4).execute() },
            { Addition(1, 2).execute() },
            { v1, v2 -> Division(v1, v2).execute().unwrap() }
        )

    fun fail1(): Result<Int, ErrorInfo> =
        zip(
            { Addition(5, 4).execute() },
            { Division(1, 0).execute() },
            { v1, v2 -> Division(v1, v2).execute().unwrap() }
        )

    fun fail2(): Result<Int, ErrorInfo> =
        zip(
            { Addition(5, 4).execute() },
            { Subtraction(1, 1).execute() },
            { v1, v2 -> Division(v1, v2).execute().unwrap() }
        )

}