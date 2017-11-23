package jp.glory.reactor.chat.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
    public RouterFunction<ServerResponse> messageRoute(MessageHandler handler) {

        return route(GET("/messages").and(accept(MediaType.APPLICATION_JSON)), handler::getMessages);
    }
}
