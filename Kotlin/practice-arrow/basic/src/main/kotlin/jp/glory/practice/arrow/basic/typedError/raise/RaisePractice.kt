package jp.glory.practice.arrow.basic.typedError.raise

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.getOrElse
import arrow.core.raise.Raise
import arrow.core.raise.ensure
import arrow.core.raise.fold
import arrow.core.raise.zipOrAccumulate
import arrow.core.right
import jp.glory.practice.arrow.basic.typedError.common.InvalidResource
import jp.glory.practice.arrow.basic.typedError.common.SaveIsFailedError
import jp.glory.practice.arrow.basic.typedError.common.TypedDomainError
import jp.glory.practice.arrow.basic.typedError.common.TypedUserStatus
import jp.glory.practice.arrow.basic.typedError.common.UserNotFoundError
import jp.glory.practice.arrow.basic.typedError.common.ValidatedError
import jp.glory.practice.arrow.basic.typedError.either.EitherUser
import jp.glory.practice.arrow.basic.typedError.either.EitherUserBirthday
import jp.glory.practice.arrow.basic.typedError.either.EitherUserId
import jp.glory.practice.arrow.basic.typedError.either.EitherUserName
import java.time.LocalDate
import java.util.UUID

fun Raise<TypedDomainError>.registerUser(
    repository: RaiseUserRepository,
    input: RaiseRegisterInput
): RaiseUserId =
    createUser(
        userName = input.userName,
        birthday = input.birthday
    )
        .also { repository.save(it) }
        .let { it.userId }

fun Raise<TypedDomainError>.findById(
    repository: RaiseUserRepository,
    userId: RaiseUserId
): RaiseUser =
    repository.findUserById(userId)
        .bind()

fun Raise<ValidatedError>.createUser(
    userName: String,
    birthday: LocalDate,
): RaiseUser =
    fold<NonEmptyList<ValidatedError>, RaiseUser, RaiseUser>(
        {
            zipOrAccumulate(
                { createUserName(userName) },
                { createUserBirthday(birthday) },
            ) { userName: RaiseUserName, birthday: RaiseUserBirthday ->
                RaiseUser(RaiseUserId.generate(), userName, birthday, TypedUserStatus.Active)
            }
        },
        { raise(ValidatedError.merge(it)) },
        { it }
    )

fun Raise<ValidatedError>.createUserId(value: String): RaiseUserId {
    ensure(value.isNotBlank()) {
        ValidatedError().addRequiredError(InvalidResource.UserId)
    }
    return RaiseUserId(value)
}

fun Raise<ValidatedError>.createUserName(value: String): RaiseUserName {
    val targetResource = InvalidResource.UserName
    ensure(value.isNotBlank()) {
        ValidatedError().addRequiredError(targetResource)
    }
    ensure(value.length <= 100) {
        ValidatedError().addMaxLengthOverError(targetResource)
    }
    return RaiseUserName(value)
}

fun Raise<ValidatedError>.createUserBirthday(value: LocalDate): RaiseUserBirthday {
    val today = LocalDate.now()
    ensure(value.isBefore(today) || value == today) {
        ValidatedError().addFutureBirthday(InvalidResource.Birthday)
    }
    return RaiseUserBirthday(value)
}

class RaiseRegisterInput(
    val userName: String,
    val birthday: LocalDate,
)

class RaiseUser(
    val userId: RaiseUserId,
    val userName: RaiseUserName,
    val birthday: RaiseUserBirthday,
    val status: TypedUserStatus
)

@JvmInline
value class RaiseUserId(val value: String) {
    companion object {
        fun generate(): RaiseUserId =
            RaiseUserId(UUID.randomUUID().toString())
    }
}
@JvmInline
value class RaiseUserName(val value: String)
@JvmInline
value class RaiseUserBirthday(val value: LocalDate)

interface RaiseUserRepository {
    fun findUserById(userId: RaiseUserId): Either<UserNotFoundError, RaiseUser>
    fun save(user: RaiseUser): Either<SaveIsFailedError, Unit>
}


class RaiseUserRepositoryImpl(
    private val users: MutableMap<RaiseUserId, RaiseUser> = mutableMapOf()
) : RaiseUserRepository {

    companion object {
        val activeUser: RaiseUser = RaiseUser(
            userId = RaiseUserId.generate(),
            userName = RaiseUserName("active-user-name"),
            birthday = RaiseUserBirthday(LocalDate.of(1986, 12, 16)),
            status = TypedUserStatus.Active
        )
    }

    override fun findUserById(userId: RaiseUserId): Either<UserNotFoundError, RaiseUser> =
        users[userId]
            ?.right()
            ?: Either.Left(UserNotFoundError)

    override fun save(user: RaiseUser): Either<SaveIsFailedError, Unit> {
        users[user.userId] = user
        return Either.Right(Unit)
    }
}