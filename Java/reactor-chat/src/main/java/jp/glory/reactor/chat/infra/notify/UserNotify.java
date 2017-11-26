package jp.glory.reactor.chat.infra.notify;

import java.util.ArrayList;
import java.util.List;

import jp.glory.reactor.chat.domain.entity.User;

/**
 * ユーザイベント通知.
 * @author gloryof
 *
 */
public class UserNotify {


    /**
     * リスナーリスト.
     */
    private final List<NotifyEventListener<User>> listeners = new ArrayList<>();

    /**
     * リスナーを追加する.
     * @param listener リスナー
     */
    public void addListener(final NotifyEventListener<User> listener) {

        listeners.add(listener);
    }

    /**
     * ユーザ追加.
     * @param user ユーザ
     */
    public void addUser(final User user) {

        listeners.forEach(v -> v.addData(user));
    }
}
