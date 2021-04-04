package jp.glory.oauth.practice.authorization.base

sealed class Either<out L, out R> {

    fun throwIfLeft(
        throwFunc: (L) -> RuntimeException
    ): R {
        return when(this) {
            is Right -> this.value
            is Left -> throw throwFunc(this.value)
        }
    }
}

fun <L, R, T> Either<L, R>.map(
    func: (R) -> T
): Either<L, T> {
    return when(this) {
        is Right -> Right(func(this.value))
        is Left -> Left(this.value)
    }
}

fun <L, R, T> Either<L, R>.flatMap(
    func: (R) -> Either<out L, out T>
): Either<L, T> {
    return when(this) {
        is Right -> func(this.value)
        is Left -> Left(this.value)
    }
}

fun <L, R, T> Either<L, R>.mapBoth(
    left: (L) -> T,
    right: (R) -> T
): T {
    return when(this) {
        is Right -> right(this.value)
        is Left -> left(this.value)
    }
}

class Right<out T>(val value: T) : Either<Nothing, T>() {}

class Left<out T>(val value: T) : Either<T, Nothing>() {}