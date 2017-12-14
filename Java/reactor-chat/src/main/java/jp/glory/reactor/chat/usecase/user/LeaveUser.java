package jp.glory.reactor.chat.usecase.user;

import org.springframework.stereotype.Component;

import jp.glory.reactor.chat.domain.repository.UserRepository;
import jp.glory.reactor.chat.domain.value.Name;

/**
 * チャットから退室する.
 * @author gloryof
 *
 */
@Component
public class LeaveUser {

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
     * 退室する.
     * @param name 退室するユーザ名
     */
    public void leave(final Name name) {

        repository.delete(name);
    }
}
