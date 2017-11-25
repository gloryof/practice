package jp.glory.reactor.chat.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * ルーティング設定.
 * @author gloryof
 *
 */
@Configuration
public class Routing {

    @Bean
    public RouterFunction<ServerResponse> routeSetting(MessageHandler handler) {

        return nest(path("/messages"), messageRoute(handler));
    }

    private RouterFunction<ServerResponse> messageRoute(MessageHandler handler) {

        return route(GET("/"), handler::getMessages)
                .andRoute(GET("/add"), handler::addMessage);
    }
}
