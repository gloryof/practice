package jp.glory.reactor.chat.infra.notify;

public interface NotifyEventListener<T> {

    void addData(T data);
}
