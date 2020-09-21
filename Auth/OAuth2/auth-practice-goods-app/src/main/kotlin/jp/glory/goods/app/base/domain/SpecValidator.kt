package jp.glory.goods.app.base.domain

import jp.glory.goods.app.base.Either
import jp.glory.goods.app.base.Left
import jp.glory.goods.app.base.Right

abstract class SpecValidator<T>(
    name: String,
    protected val value: String?
) {
    protected val error = SpecErrorDetail(name)

    fun required() {
        if (value.isNullOrEmpty()) {
            error.addRequiredError()
        }
    }

    fun whenNoError(block: (SpecValidator<T>) -> Unit) {
        if (error.hasError()) {
            block(this)
        }
    }

    fun toEither(): Either<SpecErrorDetail, T> {
        if (error.hasError()) {
            return Left(error)
        }

        return toEitherInternal()
    }

    protected abstract fun toEitherInternal(): Either<SpecErrorDetail, T>
}

class StringSpecValidator(
    name: String,
    value: String?
) : SpecValidator<String>(name, value) {

    fun maxLength(maxLength: Int) {
        val valueLength = value
            ?.let { it.codePointCount(0, it.length) }
            ?: 0

        if (valueLength > maxLength) {
            error.addMaxLengthError(maxLength)
        }
    }

    override fun toEitherInternal(): Either<SpecErrorDetail, String> {
        val result = value ?: ""
        return Right(result)
    }
}

class IntSpecValidator(
    private val name: String,
    value: String?
) : SpecValidator<Int>(name, value) {
    private val numericValue: Int? = value
        ?.let { it.toIntOrNull() }
        ?: null

    fun numeric() {
        if (numericValue == null) {
            error.addNumericError()
        }
    }

    fun positive() {
        val result = numericValue ?: 0
        if (result < 0) {
            error.add("$name is negative number.input $name positive number.")
        }
    }

    override fun toEitherInternal(): Either<SpecErrorDetail, Int> {
        val result = numericValue ?: 0
        return Right(result)
    }
}