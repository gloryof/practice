package jp.glory.reactor.chat.infra.notify;

import java.util.List;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink.OverflowStrategy;

/**
 * ユーザイベント通知.
 * @author gloryof
 *
 */
@Component
public class UserNotify {

    /**
     * イベントリスナー.
     */
    private UserEventListener listener;

    /**
     * ユーザリストFlux.
     */
    private final Flux<List<User>> flux; 

    public UserNotify() {

        this.flux = Flux.<List<User>>create(sink -> {

            listener = new UserEventListener(sink);
        }, OverflowStrategy.LATEST);
    }

    /**
     * ユーザリストを配信する.
     * @param users ユーザリスト
     */
    public void publish(final List<User> users) {

        listener.changeData(users);
    }

    /**
     * @return the flux
     */
    public Flux<List<User>> getFlux() {
        return flux;
    }
}
