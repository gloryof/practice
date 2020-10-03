package jp.glory.monitor.jvm.practice.app.base.api

object GoodsScope {
    private const val base = "SCOPE_goods"
    const val read = "$base:read"
    const val register = "$base:register"
    const val update = "$base:update"
}