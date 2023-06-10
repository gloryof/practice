package jp.glory.kotlin_result.practice.express.inspection

import com.github.michaelbull.result.or
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.orElse
import com.github.michaelbull.result.recover
import com.github.michaelbull.result.recoverIf
import jp.glory.kotlin_result.practice.express.model.Division
import jp.glory.kotlin_result.practice.express.model.ErrorInfo

object OrInspection {
    val successResult1 = Division(2, 2).execute()
    val successResult2 = Division(4, 2).execute()
    val failResult1 = Division(3, 0).execute()
    val failResult2 = Division(2, 0).execute()

    class Or {
        fun pattern1(): Result<Int, ErrorInfo> =
            successResult1.or(successResult2)

        fun pattern2(): Result<Int, ErrorInfo> =
            failResult1.or(successResult2)

        fun pattern3(): Result<Int, ErrorInfo> =
            failResult1.or(failResult2)
    }

    class OrElse {
        fun pattern1(): Result<Int, ErrorInfo> =
            successResult1.orElse { successResult2 }

        fun pattern2(): Result<Int, ErrorInfo> =
            failResult1.orElse { successResult2 }

        fun pattern3(): Result<Int, ErrorInfo> =
            failResult1.orElse {failResult2 }
    }

    class Recover {
        fun success(): Result<Int, ErrorInfo> =
            successResult1.recover { 0 }

        fun fail(): Result<Int, ErrorInfo> =
            failResult1.recover { 0 }
    }

    class RecoverIf {
        fun success(): Result<Int, ErrorInfo> =
            successResult1.recoverIf(
                { it == ErrorInfo.DIV_BY_ZERO },
                { 0 }
            )

        fun fail(): Result<Int, ErrorInfo> =
            failResult1.recoverIf(
                { it == ErrorInfo.DIV_BY_ZERO },
                { 0 }
            )
    }
}