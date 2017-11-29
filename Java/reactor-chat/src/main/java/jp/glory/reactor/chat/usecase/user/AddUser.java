package jp.glory.reactor.chat.usecase.user;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.repository.UserRepository;

/**
 * ユーザを追加する.
 * @author gloryof
 *
 */
@Component
public class AddUser {

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
     * ユーザを追加する.
     * @param user ユーザ
     */
    public void addUser(final User user) {

        repository.add(user);
        repository.notifyToUsers();
    }
}
