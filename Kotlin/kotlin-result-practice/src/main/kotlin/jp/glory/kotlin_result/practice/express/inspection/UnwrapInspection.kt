package jp.glory.kotlin_result.practice.express.inspection

import com.github.michaelbull.result.expect
import com.github.michaelbull.result.expectError
import com.github.michaelbull.result.unwrap
import com.github.michaelbull.result.unwrapError
import jp.glory.kotlin_result.practice.express.model.Division
import jp.glory.kotlin_result.practice.express.model.ErrorInfo

object UnwrapInspection {
    val successResult = Division(2, 2).execute()
    val failResult = Division(3, 0).execute()

    class Unwrap {
        fun success(): Int =
            successResult.unwrap()

        fun fail(): Int =
            failResult.unwrap()
    }

    class Expect {
        fun success(): Int =
            successResult.expect { "message" }

        fun fail(): Int =
            failResult.expect { "message" }
    }

    class UnwrapError {
        fun success(): ErrorInfo =
            successResult.unwrapError()

        fun fail(): ErrorInfo =
            failResult.unwrapError()
    }

    class ExpectError {
        fun success(): ErrorInfo =
            successResult.expectError { "message" }

        fun fail(): ErrorInfo =
            failResult.expectError { "message" }
    }
}