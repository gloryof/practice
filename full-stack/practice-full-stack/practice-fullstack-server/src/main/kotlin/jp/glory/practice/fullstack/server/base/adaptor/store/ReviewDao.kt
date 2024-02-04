package jp.glory.practice.fullstack.server.base.adaptor.store

class ReviewDao {
    private val reviews = mutableMapOf<String, ReviewRecord>()

    fun findById(id: String): ReviewRecord? = reviews[id]

    fun findByUserId(userId: String): List<ReviewRecord> =
        reviews
            .filter { it.key == userId }
            .map { it.value }
            .toList()


    fun save(record: ReviewRecord) {
        reviews[record.id] = record
    }
}


class ReviewRecord(
    val id: String,
    val userId: String,
    val title: String,
    val rating: UInt
)