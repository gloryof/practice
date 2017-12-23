package jp.glory.reactor.chat.usecase.user;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.repository.UserRepository;

/**
 * ユーザを追加する.
 * @author gloryof
 *
 */
@Component
public class AddUser implements Consumer<User> {

    /**
     * ユーザリポジトリ.
     */
    private final UserRepository repository;

    /**
     * コンストラクタ.
     * @param repository リポジトリ
     */
    public AddUser(final UserRepository repository) {

        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public void accept(final User user) {

        repository.add(user);
        repository.notifyToUsers();
    }
}
