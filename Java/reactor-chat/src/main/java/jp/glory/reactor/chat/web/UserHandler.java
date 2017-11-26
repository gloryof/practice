package jp.glory.reactor.chat.web;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.repository.UserRepository;
import jp.glory.reactor.chat.domain.value.ChatType;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.NotifyEventListener;
import jp.glory.reactor.chat.infra.notify.UserNotify;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.FluxSink.OverflowStrategy;

/**
 * ユーザハンドラ.
 * @author gloryof
 *
 */
@Component
public class UserHandler {

    /**
     * リポジトリ.
     */
    private final UserRepository repository;

    /**
     * ユーザ通知.
     */
    private final UserNotify notify = new UserNotify();

    /**
     * ユーザ一覧fluxオブジェクト.
     */
    private final Flux<List<User>> flux;

    /**
     * コンストラクタ.
     * @param repository リポジトリ
     */
    public UserHandler(final UserRepository repository) {

        this.repository = repository;
        this.flux = Flux.<List<User>>create(sink -> {

            sink.next(repository.findAll());
            notify.addListener(new NotifyEventListener<User>() {
                
                @Override
                public void addData(User data) {

                    sink.next(repository.findAll());
                }
            });
        }, OverflowStrategy.LATEST)
        .log();
    }

    /**
     * ユーザのリストを取得する.
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> getUsers(final ServerRequest request) {

        final ParameterizedTypeReference<List<User>> type = new ParameterizedTypeReference<List<User>>() {};

        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(flux, type);
    }

    /**
     * ユーザを追加する.
     * @param request リクエスト
     * @return レスポンス
     */
    public Mono<ServerResponse> addUser(final ServerRequest request) {

        String value = request.queryParam("name").orElse("");
        final User user = new User(new Name(value), ChatType.Cold);

        repository.add(user);
        notify.addUser(user);

        return ServerResponse.ok().build();
    }
}
