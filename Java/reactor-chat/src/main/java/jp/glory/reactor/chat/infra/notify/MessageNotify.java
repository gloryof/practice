package jp.glory.reactor.chat.infra.notify;

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
     * イベントリスナー.
     */
    private MessageEventListener listener;

    /**
     * メッセージリストFluxオブジェクト.
     */
    private final Flux<Message> flux;

    /**
     * コンストラクタ.
     */
    public MessageNotify() {

        flux = Flux.<Message>create(sink -> {

            listener = new MessageEventListener(sink);
        }).share();
    }


    /**
     * メッセージを配信する.
     * @param message メッセージ
     */
    public void publish(final Message message) {

        listener.pushNewMessage(message);
    }

    /**
     * @return the flux
     */
    public Flux<Message> getFlux() {
        return flux;
    }

    /**
     * 完了通知を行う.
     */
    void complete() {

        listener.complete();
    }
}
