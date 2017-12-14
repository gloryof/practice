package jp.glory.reactor.chat.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
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

    /**
     * ルート部分ルーティング設定.
     * @param messageHandler メッセージハンドラー
     * @param userHandler ユーザハンドラー
     * @return ルータファンクション
     */
    @Bean
    public RouterFunction<ServerResponse> routeSetting(final MessageHandler messageHandler,
            final UserHandler userHandler) {

        return nest(path("/messages"), messageRoute(messageHandler))
                .andNest(path("/users"), userRoute(userHandler));
    }

    /**
     * メッセージ周りのルーティング設定
     * @param handler メッセージハンドラー
     * @return ルータファンクション
     */
    private RouterFunction<ServerResponse> messageRoute(final MessageHandler handler) {

        return route(GET("/"), handler::getMessages)
                .andRoute(POST("/add"), handler::addMessage);
    }

    /**
     * ユーザ周りのルーティング設定
     * @param handler メッセージハンドラー
     * @return ルータファンクション
     */
    private RouterFunction<ServerResponse> userRoute(final UserHandler handler) {

        return route(GET("/"), handler::getUsers)
                .andRoute(POST("/add"), handler::addUser)
                .andRoute(POST("/leave"), handler::leave);
    }
}
