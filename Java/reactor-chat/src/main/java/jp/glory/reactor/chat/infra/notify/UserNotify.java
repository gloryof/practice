package jp.glory.reactor.chat.infra.notify;

import java.util.ArrayList;
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
     * リスナーリスト.
     */
    private final List<NotifyEventListener<List<User>>> listeners = new ArrayList<>();

    /**
     * ユーザリストFlux.
     */
    private final Flux<List<User>> flux; 

    public UserNotify() {

        this.flux = Flux.<List<User>>create(sink -> {

            listeners.add(new NotifyEventListener<List<User>>() {
                
                @Override
                public void addData(List<User> data) {

                    sink.next(data);
                }
            });
        }, OverflowStrategy.LATEST)
        .log();
    }

    /**
     * ユーザリストを配信する.
     * @param users ユーザリスト
     */
    public void publish(final List<User> users) {

        listeners.forEach(v -> v.addData(users));
    }

    /**
     * @return the flux
     */
    public Flux<List<User>> getFlux() {
        return flux;
    }
}
