package jp.glory.reactor.chat.infra.notify;

/**
 * 通知イベントリスナー.
 * @author gloryof
 *
 * @param <T> イベントデータ
 */
public interface NotifyEventListener<T> {

    /**
     * データを追加する.
     * @param data イベントデータ
     */
    void addData(T data);
}
