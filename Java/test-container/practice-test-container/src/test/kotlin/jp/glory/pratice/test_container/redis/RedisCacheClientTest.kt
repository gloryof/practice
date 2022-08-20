package jp.glory.pratice.test_container.redis

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
internal class RedisCacheClientTest {
    private val targetPort = 6379

    @Container
    private val redis: GenericContainer<*> = GenericContainer(DockerImageName.parse("redis:alpine:7.0.4-alpine"))
        .withExposedPorts(targetPort)

    @Nested
    inner class TestSet {

        @Test
        fun whenSuccessThenValueIsGet() {
            val sut = createSut()
            val key = "test-set-key"
            val value = "test-set-value"

            sut.set(key, value)

            val actual = redis
                .execInContainer("redis-cli", "get", key)
                .stdout.trim() // For delete line-feed

            assertEquals(value, sut.get(key))
            assertEquals(value, actual)
        }
    }
    @Nested
    inner class TestGet {

        @Test
        fun whenSuccessThenReturnValue() {
            val sut = createSut()
            val key = "test-get-key"
            val value = "test-get-value"


            redis.execInContainer("redis-cli", "set" , key, value)

            assertEquals(value, sut.get(key))
        }
    }

    private fun createSut(): RedisCacheClient = RedisCacheClient(
        host = redis.host,
        port = redis.firstMappedPort
    )
}