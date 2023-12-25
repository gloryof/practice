package jp.glory.practice.grpc.user.domain.repository

import jp.glory.practice.grpc.user.domain.model.User
import jp.glory.practice.grpc.user.domain.model.UserId

interface UserRepository {
    fun findById(id: UserId): User?
    fun findAllUsers(): List<User>
}