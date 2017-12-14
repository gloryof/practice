package jp.glory.reactor.chat.infra.notify;

import java.util.List;

import jp.glory.reactor.chat.domain.entity.User;
import reactor.core.publisher.FluxSink;

/**
 * ユーザイベントリスナー.
 * @author gloryof
 *
 */
class UserEventListener {

    /**
     * Flux Sink.
     */
    private final FluxSink<List<User>> sink;

    /**
     * コンストラクタ.
     * @param sink Flux sink
     */
    UserEventListener(final FluxSink<List<User>> sink) {

        this.sink = sink;
    }

    /**
     * データの変更通知を行う.<br>
     * @param data データ
     */
    void changeData(final List<User> data) {

        sink.next(data);
    }
}
