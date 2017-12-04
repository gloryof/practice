package jp.glory.reactor.chat.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.UserNotify;
import jp.glory.reactor.chat.usecase.user.AddUser;
import jp.glory.reactor.chat.web.request.UserRequest;
import jp.glory.reactor.chat.web.response.UsersResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ユーザハンドラ.
 * @author gloryof
 *
 */
@Component
public class UserHandler {

    /**
     * ユーザ追加ユースケース.
     */
    private final AddUser addUser;

    /**
     * ユーザ通知.
     */
    private final UserNotify notify;

    /**
     * コンストラクタ.
     * @param addUser ユーザ追加ユースケース
     * @param notify ユーザ通知
     */
    public UserHandler(final AddUser addUser, final UserNotify notify) {

        this.addUser = addUser;
        this.notify = notify;
    }

    /**
     * ユーザのリストを取得する.
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> getUsers(final ServerRequest request) {

        Flux<UsersResponse> flux = notify.getFlux().map(UsersResponse::new);

        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, UsersResponse.class);
    }

    /**
     * ユーザを追加する.
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> addUser(final ServerRequest request) {

        request.bodyToMono(UserRequest.class)
            .map(v -> new User(new Name(v.getName()), v.getType()))
            .subscribe(addUser::addUser);

        return ServerResponse.ok().build();
    }
}
