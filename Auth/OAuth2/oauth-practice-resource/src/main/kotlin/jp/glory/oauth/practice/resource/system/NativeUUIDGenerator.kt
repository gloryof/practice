package jp.glory.oauth.practice.resource.system

import jp.glory.oauth.practice.resource.base.system.UUIDGenerator
import org.springframework.stereotype.Component
import java.util.*

@Component
class NativeUUIDGenerator : UUIDGenerator {
    override fun generate(): String = UUID.randomUUID().toString()
}