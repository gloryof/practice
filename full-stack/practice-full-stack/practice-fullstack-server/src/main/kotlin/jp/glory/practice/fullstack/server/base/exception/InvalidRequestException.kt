package jp.glory.practice.fullstack.server.base.exception

class InvalidRequestException(
    override val message: String
) : Exception(message)