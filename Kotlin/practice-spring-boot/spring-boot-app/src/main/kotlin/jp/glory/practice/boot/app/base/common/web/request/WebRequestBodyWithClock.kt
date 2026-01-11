package jp.glory.practice.boot.app.base.common.web.request

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import jp.glory.practice.boot.app.base.common.web.exception.WebErrors
import java.time.Clock

abstract class WebRequestBodyWithClock<T> {
    fun parse(clock: Clock): Result<T, WebErrors> =
        validate(clock)
            .map { convert() }

    abstract fun validate(clock: Clock): Result<Unit, WebErrors>
    abstract fun convert(): T
}
