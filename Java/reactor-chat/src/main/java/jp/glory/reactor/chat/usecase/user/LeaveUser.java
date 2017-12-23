package jp.glory.reactor.chat.usecase.user;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.repository.UserRepository;
import jp.glory.reactor.chat.domain.value.Name;

/**
 * チャットから退室する.
 * @author gloryof
 *
 */
@Component
public class LeaveUser implements Consumer<Name> {

    /**
     * ユーザリポジトリ.
     */
    private final UserRepository repository;

    /**
     * コンストラクタ.
     * @param repository ユーザリポジトリ
     */
    public LeaveUser(final UserRepository repository) {

        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final Name name) {

        repository.delete(name);
    }
}
