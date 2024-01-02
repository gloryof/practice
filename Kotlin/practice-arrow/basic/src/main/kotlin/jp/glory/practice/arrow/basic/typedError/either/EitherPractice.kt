package jp.glory.practice.arrow.basic.typedError.either

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.getOrElse
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import arrow.core.right
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.SaveIsFailedError
import jp.glory.practice.arrow.basic.typedError.common.TypedDomainError
import jp.glory.practice.arrow.basic.typedError.common.TypedUserStatus
import jp.glory.practice.arrow.basic.typedError.common.UserNotFoundError
import jp.glory.practice.arrow.basic.typedError.common.ValidatedError
import java.time.LocalDate
import java.util.UUID

class EitherPractice(
    private val repository: EitherUserRepository
) {
    fun findById(userId: EitherUserId): Either<UserNotFoundError, EitherUser> = repository.findUserById(userId)

    fun register(
        input: RegisterInput
    ): Either<TypedDomainError, EitherUserId> =
        EitherUser.create(
            userName = input.userName,
            birthday = input.birthday
        )
            .flatMap {
                repository.save(it)
                it.userId.right()
            }

    class RegisterInput(
        val userName: String,
        val birthday: LocalDate,
    )
}

class EitherUser private constructor(
    val userId: EitherUserId,
    val userName: EitherUserName,
    val birthday: EitherUserBirthday,
    val status: TypedUserStatus
) {
    companion object {
        fun create(
            userName: String,
            birthday: LocalDate,
        ): Either<ValidatedError, EitherUser> = either<NonEmptyList<ValidatedError>, EitherUser> {
            zipOrAccumulate(
                { EitherUserName.create(userName).bind() },
                { EitherUserBirthday.create(birthday).bind() },
            ) { userName, birthday ->
                EitherUser(EitherUserId.generate(), userName, birthday, TypedUserStatus.Active)
            }
        }
            .mapLeft { ValidatedError.merge(it) }

    }
}

@JvmInline
value class EitherUserId private constructor(val value: String) {
    companion object {
        private val targetResource = InvalidResource.UserId
        fun create(value: String): Either<ValidatedError, EitherUserId> = either {
            ensure(value.isNotBlank()) {
                ValidatedError().addRequiredError(targetResource)
            }
            EitherUserId(value)
        }

        fun generate(): EitherUserId =
            EitherUserId(UUID.randomUUID().toString())
    }
}

@JvmInline
value class EitherUserName private constructor(val value: String) {
    companion object {
        private val targetResource = InvalidResource.UserName
        fun create(value: String): Either<ValidatedError, EitherUserName> = either {
            ensure(value.isNotBlank()) {
                ValidatedError().addRequiredError(targetResource)
            }
            ensure(value.length <= 100) {
                ValidatedError().addMaxLengthOverError(targetResource)
            }
            EitherUserName(value)
        }
    }
}

@JvmInline
value class EitherUserBirthday private constructor(val value: LocalDate) {

    companion object {
        private val targetResource = InvalidResource.Birthday
        fun create(value: LocalDate): Either<ValidatedError, EitherUserBirthday> = either {
            val today = LocalDate.now()
            ensure(value.isBefore(today) || value == today) {
                ValidatedError().addFutureBirthday(targetResource)
            }
            EitherUserBirthday(value)
        }
    }
}

interface EitherUserRepository {
    fun findUserById(userId: EitherUserId): Either<UserNotFoundError, EitherUser>
    fun save(user: EitherUser): Either<SaveIsFailedError, Unit>
}

class EitherUserRepositoryImpl(
    private val users: MutableMap<EitherUserId, EitherUser> = mutableMapOf()
) : EitherUserRepository {
    companion object {
        val activeUser: EitherUser = EitherUser.create(
            userName = "active-user-name",
            birthday = LocalDate.of(1986, 12, 16),
        )
            .getOrElse { throw IllegalArgumentException("Invalid data") }
    }

    override fun findUserById(userId: EitherUserId): Either<UserNotFoundError, EitherUser> =
        users[userId]
            ?.right()
            ?: Either.Left(UserNotFoundError)

    override fun save(user: EitherUser): Either<SaveIsFailedError, Unit> {
        users[user.userId] = user
        return Either.Right(Unit)
    }
}