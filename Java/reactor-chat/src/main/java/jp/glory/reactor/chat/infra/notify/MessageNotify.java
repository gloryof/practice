package jp.glory.reactor.chat.infra.notify;

import java.util.ArrayList;
import java.util.List;

import jp.glory.reactor.chat.domain.entity.Message;

/**
 * メッセージイベント通知.
 * @author gloryof
 *
 */
public class MessageNotify {

    /**
     * リスナーリスト.
     */
    private final List<NotifyEventListener<Message>> listeners = new ArrayList<>();

    /**
     * リスナーを追加する.
     * @param listener リスナー
     */
    public void addListener(final NotifyEventListener<Message> listener) {

        listeners.add(listener);
    }

    /**
     * メッセージを追加する.
     * @param message メッセージ
     */
    public void addMessage(final Message message) {

        listeners.stream().forEach(v -> v.addData(message));
    }
}
