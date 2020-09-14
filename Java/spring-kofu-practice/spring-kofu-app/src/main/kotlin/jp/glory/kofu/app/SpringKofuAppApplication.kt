package jp.glory.kofu.app

import jp.glory.kofu.app.person.PersonConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.reactiveWebApplication
import org.springframework.fu.kofu.redis.redis
import org.springframework.fu.kofu.webflux.webFlux

@SpringBootApplication
class SpringKofuAppApplication

fun main(args: Array<String>) {
    app().run()
}

fun app() = reactiveWebApplication {
    enable(PersonConfig.config())
    enable(webConfig())
    enable(infraConfig())
}

fun webConfig() = configuration {
    webFlux {
        port = env.getProperty("APP_BOOT_PORT", Int::class.java, 8080)
        codecs {
            string()
            jackson()
        }
    }
}

fun infraConfig() = configuration {
    redis {
        host = env.getProperty("APP_REDIS_HOST", "localhost")
        port = env.getProperty("APP_REDIS_PORT", Int::class.java, 6379)
    }
}

data class BootConfig(
    val port: String
)