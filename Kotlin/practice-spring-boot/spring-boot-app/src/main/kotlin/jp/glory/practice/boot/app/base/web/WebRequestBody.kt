package jp.glory.practice.boot.app.base.web

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map

abstract class WebRequestBody<T> {
    fun parse(): Result<T, WebErrors> =
        validate()
            .map { convert() }

    abstract fun validate(): Result<Unit, WebErrors>
    abstract fun convert(): T
}
