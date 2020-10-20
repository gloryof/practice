package jp.glory.k8s.mesh.app.base

sealed class Either<L, R> {
    fun <T> mapBoth(
        left: (L) -> T,
        right: (R) -> T
    ): T {
        return when(this) {
            is Right -> right(this.value)
            is Left -> left(this.value)
        }
    }

    fun throwIfLeft(
        throwFunc: (L) -> RuntimeException
    ): R {
        return when(this) {
            is Right -> this.value
            is Left -> throw throwFunc(this.value)
        }
    }
}

class Right<L, R>(val value: R) : Either<L, R>() {}

class Left<L,R >(val value: L) : Either<L, R>() {}