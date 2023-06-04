package jp.glory.kotlin_result.practice.express.inspection

import com.github.michaelbull.result.binding
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import jp.glory.kotlin_result.practice.express.model.Addition
import jp.glory.kotlin_result.practice.express.model.Division
import jp.glory.kotlin_result.practice.express.model.ErrorInfo
import jp.glory.kotlin_result.practice.express.model.Multiplication
import jp.glory.kotlin_result.practice.express.model.Subtraction

class BindingInspection {
    /**
     * 1 + 2 - 3 + 4 - 5 = -1
     */
    class SuccessPattern1 {
        fun pattern1(): Result<Int, ErrorInfo> =
            Addition(1, 2).execute()
                .flatMap { Subtraction(it, 3).execute() }
                .flatMap { Addition(it, 4).execute() }
                .flatMap { Subtraction(it, 5).execute() }

        fun pattern2(): Result<Int, ErrorInfo> =
            binding {
                val a = Addition(1, 2).execute().bind()
                val b = Subtraction(a, 3).execute().bind()
                val c = Addition(b, 4).execute().bind()
                val d = Subtraction(c, 5).execute().bind()
                d
            }

        fun pattern3(): Result<Int, ErrorInfo> =
            binding {
                val a = Addition(1, 2).execute().bind()
                val b = Subtraction(a, 3).execute().bind()
                val c = Addition(b, 4).execute().bind()
                Subtraction(c, 5).execute().bind()
            }
    }
    /**
     * ((5 + 4) * 3) / (2 + 1) = 9
     */
    class SuccessPattern2 {

        fun pattern1(): Result<Int, ErrorInfo> {
            val firstExpress = Addition(5, 4).execute()
                .flatMap { Multiplication(it, 3).execute() }

            val secondExpress = Addition(2, 1).execute()

            return firstExpress
                .flatMap { first ->
                    secondExpress.flatMap { second ->
                        Division(first, second).execute()
                    }
                }
        }

        fun pattern2(): Result<Int, ErrorInfo> =
            binding {
                val a = Addition(5, 4).execute().bind()
                val firstExpress = Multiplication(a, 3).execute().bind()
                val secondExpress = Addition(2, 1).execute().bind()
                Division(firstExpress, secondExpress).execute().bind()
            }
    }

    class FailedPattern1 {
        fun failPattern1(): Result<Int, ErrorInfo> =
            binding {
                val x = Addition(1, 2).execute().bind()
                Division(x, 0).execute().bind()
            }
    }
}