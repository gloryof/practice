package jp.glory.practice.boot.app.base.common.web.request

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import jp.glory.practice.boot.app.base.common.web.exception.WebErrors

abstract class WebRequestBody<T> {
    fun parse(): Result<T, WebErrors> =
        validate()
            .map { convert() }

    abstract fun validate(): Result<Unit, WebErrors>
    abstract fun convert(): T
}
