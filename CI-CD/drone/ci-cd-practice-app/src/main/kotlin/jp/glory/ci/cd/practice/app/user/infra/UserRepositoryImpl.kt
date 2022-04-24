package jp.glory.ci.cd.practice.app.user.infra

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import jp.glory.ci.cd.practice.app.auth.infra.RegisterCredentialDao
import jp.glory.ci.cd.practice.app.auth.infra.RegisterCredentialInfo
import jp.glory.ci.cd.practice.app.auth.infra.TokenDao
import jp.glory.ci.cd.practice.app.base.domain.DomainUnknownError
import jp.glory.ci.cd.practice.app.user.domain.model.*
import jp.glory.ci.cd.practice.app.user.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val registerCredentialDao: RegisterCredentialDao,
    private val tokenDao: TokenDao
) : UserRepository {
    private val systemUser = User(
        userId = UserId("test-system-user-id"),
        givenName = GivenName("system-given-name"),
        familyName = FamilyName("system-family-name"),
    )
    private val referenceOnlyUser = User(
        userId = UserId("test-reference-only-user-id"),
        givenName = GivenName("reference-only-given-name"),
        familyName = FamilyName("reference-only-family-name"),
    )
    private val forUpdateUser = User(
        userId = UserId("test-for-update-user-id"),
        givenName = GivenName("for-update-given-name"),
        familyName = FamilyName("for-update-family-name"),
    )
    private val users = mutableMapOf(
        systemUser.userId.value to systemUser,
        referenceOnlyUser.userId.value to referenceOnlyUser,
        forUpdateUser.userId.value to forUpdateUser
    )
    init {
        registerCredentialDao.save(
            RegisterCredentialInfo(
                credentialUserId = systemUser.userId.value,
                givenName = systemUser.givenName.value,
                familyName = systemUser.familyName.value,
                password = "test-system-password"
            )
        )
    }

    override fun findById(id: UserId): Result<User?, DomainUnknownError> =
        Ok(users[id.value])

    override fun register(event: RegisterUserEvent): Result<UserId, DomainUnknownError> {
        users[event.userId.value] = User(
            userId = event.userId,
            givenName = event.givenName,
            familyName = event.familyName,
        )
        registerCredentialDao.save(
            RegisterCredentialInfo(
                credentialUserId = event.userId.value,
                givenName = event.givenName.value,
                familyName = event.familyName.value,
                password = event.password.value
            )
        )
        return Ok(event.userId)
    }

    override fun update(event: UpdateUserEvent): Result<UserId, DomainUnknownError> {
        users[event.userId.value] = User(
            userId = event.userId,
            givenName = event.givenName,
            familyName = event.familyName,
        )
        return Ok(event.userId)
    }

    override fun delete(id: UserId): Result<Unit, DomainUnknownError> {
        users.remove(id.value)
        registerCredentialDao.delete(id.value)
        tokenDao.deleteByUserId(id.value)
        return Ok(Unit)
    }
}