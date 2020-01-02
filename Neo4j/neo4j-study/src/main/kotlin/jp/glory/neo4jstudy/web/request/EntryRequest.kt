package jp.glory.neo4jstudy.web.request

import java.time.LocalDate

/**
 * 入社リクエスト.
 *
 *
 */
data class EntryRequest(
    var entryAt: LocalDate = LocalDate.MAX,
    var lastName: String = "",
    var firstName: String = "",
    var age:Int = 0,
    var postId: Long = 0
)