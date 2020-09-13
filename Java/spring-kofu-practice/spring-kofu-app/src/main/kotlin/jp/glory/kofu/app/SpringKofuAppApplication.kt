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
        port = 8080
        codecs {
            string()
            jackson()
        }
    }
}

fun infraConfig() = configuration {
    redis {
        host = "localhost"
        port = 6379
    }
}