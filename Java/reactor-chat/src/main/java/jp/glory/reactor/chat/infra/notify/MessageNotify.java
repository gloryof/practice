package jp.glory.reactor.chat.infra.notify;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.Message;
import reactor.core.publisher.Flux;

/**
 * メッセージイベント通知.
 * @author gloryof
 *
 */
@Component
public class MessageNotify {

    /**
     * リスナーリスト.
     */
    private final List<NotifyEventListener<Message>> listeners = new ArrayList<>();

    /**
     * メッセージリストFluxオブジェクト.
     */
    private final Flux<Message> flux;

    public MessageNotify() {

        flux = Flux.<Message>create(sink -> {

            listeners.add(new NotifyEventListener<Message>() {

                @Override
                public void addData(Message data) {

                    sink.next(data);
                }
            });
        })
        .log();
    }


    /**
     * メッセージを配信する.
     * @param message メッセージ
     */
    public void publish(final Message message) {

        listeners.stream().forEach(v -> v.addData(message));
    }


    /**
     * @return the flux
     */
    public Flux<Message> getFlux() {
        return flux;
    }
}
