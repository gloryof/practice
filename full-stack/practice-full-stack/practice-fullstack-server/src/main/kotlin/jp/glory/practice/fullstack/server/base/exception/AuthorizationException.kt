package jp.glory.practice.fullstack.server.base.exception

class AuthorizationException(
    override val message: String
) : Exception(message)