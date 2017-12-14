package jp.glory.reactor.chat.infra.notify;

import jp.glory.reactor.chat.domain.entity.Message;
import reactor.core.publisher.FluxSink;

/**
 * メッセージイベントリスナー.
 * @author gloryof
 *
 */
public class MessageEventListener {

    /**
     * Flux Sink.
     */
    private final FluxSink<Message> sink;

    /**
     * コンストラクタ.
     * @param sink Sink
     */
    public MessageEventListener(final FluxSink<Message> sink) {

        this.sink = sink;
    }

    /**
     * 新しいメッセージをpushする.
     * @param message メッセージ
     */
    public void pushNewMessage(final Message message) {

        sink.next(message);
    }
}
