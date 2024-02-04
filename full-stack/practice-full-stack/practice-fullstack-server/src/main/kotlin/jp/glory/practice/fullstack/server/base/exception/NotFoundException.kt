package jp.glory.practice.fullstack.server.base.exception

class NotFoundException(
    override val message: String
) : Exception(message)