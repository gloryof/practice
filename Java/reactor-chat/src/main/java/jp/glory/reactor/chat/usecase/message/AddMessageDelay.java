package jp.glory.reactor.chat.usecase.message;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.repository.MessageRepository;

/**
 * 遅延させて複数件数通知する.
 * @author gloryof
 *
 */
@Component
public class AddMessageDelay implements Consumer<Message> {


    /**
     * メッセージリポジトリ.
     */
    private final MessageRepository messageRepository;

    /**
     * コンストラクタ.
     * @param messageRepository メッセージリポジトリ
     */
    public AddMessageDelay(final MessageRepository messageRepository) {

        this.messageRepository = messageRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final Message message) {

        List<Message> delayMessages = IntStream.rangeClosed(1, 5)
                                        .mapToObj(message::appendPrefix)
                                        .collect(Collectors.toList());

        messageRepository.notifyDelayToUser(delayMessages);
        
    }

}
