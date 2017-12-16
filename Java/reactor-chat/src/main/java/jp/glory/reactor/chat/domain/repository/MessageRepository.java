package jp.glory.reactor.chat.domain.repository;

import java.util.List;

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

    /**
     * 遅延させて複数のメッセージをユーザに通知する.
     * @param messages メッセージリスト
     */
    void notifyDelayToUser(final List<Message> messages);
}
