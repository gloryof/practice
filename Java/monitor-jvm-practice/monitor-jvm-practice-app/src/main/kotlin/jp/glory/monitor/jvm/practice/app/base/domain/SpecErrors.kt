package jp.glory.monitor.jvm.practice.app.base.domain

import jp.glory.monitor.jvm.practice.app.base.Either
import jp.glory.monitor.jvm.practice.app.base.Right

class SpecErrors() {
    private val errors: MutableList<SpecErrorDetail> = mutableListOf()

    fun add(detail :SpecErrorDetail) = errors.add(detail)
    fun <T> validateWith(
        validator: () -> Either<SpecErrorDetail, T>,
        right: (T) -> Unit
    ) =
        validator()
        .mapBoth(
            right = { right(it) },
            left = { errors.add(it) }
        )

    fun toList(): List<SpecErrorDetail> = errors.toList()
    fun hasError(): Boolean = errors.isNotEmpty()
}

class SpecErrorDetail(
    val itemName: String
) {
    private val messages: MutableList<String> = mutableListOf()

    fun getMessages(): List<String> = messages.toList()

    fun hasError() = messages.isNotEmpty()

    fun addRequiredError() = add("$itemName is required.Input $itemName.")
    fun addMaxLengthError(maxLength: Int) =
        add("$itemName is over $maxLength.Input $itemName length less than $maxLength.")
    fun addNumericError() = add("$itemName is numeric.Input $itemName to numeric.")

    fun add(message: String) = messages.add(message)
}