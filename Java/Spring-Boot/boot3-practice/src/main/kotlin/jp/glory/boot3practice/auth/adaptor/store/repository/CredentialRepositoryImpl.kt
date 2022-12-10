package jp.glory.boot3practice.auth.adaptor.store.repository

import jp.glory.boot3practice.auth.adaptor.store.dao.AuthenticationDao
import jp.glory.boot3practice.auth.domain.model.Credential
import jp.glory.boot3practice.auth.domain.model.Password
import jp.glory.boot3practice.auth.domain.model.UserId
import jp.glory.boot3practice.auth.domain.repository.CredentialRepository
import org.springframework.stereotype.Repository

@Repository
class CredentialRepositoryImpl(
    private val dao: AuthenticationDao
) : CredentialRepository {
    override fun findById(userId: UserId): Credential? =
        dao.findById(userId.value)
            ?.let {
                Credential(
                    id = UserId(it.id),
                    password = Password(it.password)
                )
            }
}