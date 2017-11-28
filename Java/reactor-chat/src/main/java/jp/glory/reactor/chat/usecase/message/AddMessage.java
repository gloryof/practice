package jp.glory.reactor.chat.usecase.message;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.repository.MessageRepository;

/**
 * メッセージを追加する.
 * @author gloryof
 *
 */
@Component
public class AddMessage {

    /**
     * メッセージリポジトリ.
     */
    private final MessageRepository messageRepository;

    /**
     * コンストラクタ.
     * @param messageRepository メッセージリポジトリ
     */
    public AddMessage(final MessageRepository messageRepository) {

        this.messageRepository = messageRepository;
    }

    /**
     * メッセージを追加する.
     * @param message メッセージ
     */
    public void add(final Message message) {

        messageRepository.notifyToUser(message);
    }
}
