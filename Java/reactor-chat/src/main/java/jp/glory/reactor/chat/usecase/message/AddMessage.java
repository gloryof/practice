package jp.glory.reactor.chat.usecase.message;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.repository.MessageRepository;

/**
 * メッセージを追加する.
 * @author gloryof
 *
 */
@Component
public class AddMessage implements Consumer<Message> {

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
     * {@inheritDoc}
     */
    @Override
    public void accept(final Message message) {

        messageRepository.notifyToUser(message);
    }
}
