package jp.glory.reactor.chat.domain.repository;

import jp.glory.reactor.chat.domain.entity.Message;

/**
 * メッセージリポジトリ.
 * @author gloryof
 *
 */
public interface MessageRepository {

    /**
     * メッセージをユーザに通知する.
     * @param message メッセージ
     */
    void notifyToUser(final Message message);
}
