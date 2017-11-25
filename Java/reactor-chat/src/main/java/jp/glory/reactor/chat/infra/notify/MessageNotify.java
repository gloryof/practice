package jp.glory.reactor.chat.infra.notify;

import java.util.ArrayList;
import java.util.List;

import jp.glory.reactor.chat.domain.entity.Message;

public class MessageNotify {

    private final List<NotifyEventListener<Message>> listeners = new ArrayList<>();

    public void addListener(final NotifyEventListener<Message> listener) {

        listeners.add(listener);
    }

    public void addMessage(final Message message) {

        listeners.stream().forEach(v -> v.addData(message));
    }
}
