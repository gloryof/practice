package jp.glory.oauth.practice.authorization.base

import java.time.OffsetDateTime
import java.time.ZoneOffset

object DateTime {
    fun getDefaultOffset(): ZoneOffset =
        OffsetDateTime.now().offset
}