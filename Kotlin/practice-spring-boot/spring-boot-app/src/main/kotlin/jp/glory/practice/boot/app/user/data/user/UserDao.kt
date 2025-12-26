package jp.glory.practice.boot.app.user.data.user

class UserDao(
    private val table: MutableMap<String, UserRecord> = mutableMapOf()
) {
    fun insert(record: UserRecord) {
        table.put(record.userId, record)
    }
}
