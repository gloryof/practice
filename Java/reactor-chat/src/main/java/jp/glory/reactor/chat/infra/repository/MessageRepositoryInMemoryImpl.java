package jp.glory.reactor.chat.infra.repository;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Repository;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.repository.MessageRepository;
import jp.glory.reactor.chat.infra.notify.MessageNotify;
import reactor.core.publisher.Flux;

/**
 * メッセージリポジトリインメモリ実装.
 * @author gloryof
 *
 */
@Repository
public class MessageRepositoryInMemoryImpl implements MessageRepository {

    /**
     * メッセージ通知.
     */
    private final MessageNotify notify;

    /**
     * コンストラクタ.
     * @param notify メッセージ通知
     */
    public MessageRepositoryInMemoryImpl(final MessageNotify notify) {

        this.notify = notify;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyToUser(final Message message) {

        notify.publish(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDelayToUser(final List<Message> messages) {

        Flux.fromIterable(messages)
            .delayElements(Duration.ofSeconds(2))
            .subscribe(this::notifyToUser);
    }

}
