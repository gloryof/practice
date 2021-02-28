package jp.glory.oauth.practice.resource.system.redis

class RedisUserSchema {
    companion object {
        fun generateKey(userId: String) = "user:$userId"
    }

    data class UserData(
        val name: String
    )
}

class RedisHobbySchema {
    companion object {
        fun generateKey(userId: String) = "hobby:$userId"
    }

    data class HobbyData(
        val hobbies: List<String>
    )
}

class RedisAuthSchema {
    companion object {
        fun generateKey(userId: String) = "auth:$userId"
    }

    data class AuthData(
        val password: String
    )
}