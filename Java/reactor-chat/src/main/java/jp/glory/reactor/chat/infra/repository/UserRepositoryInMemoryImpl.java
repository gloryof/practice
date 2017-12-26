package jp.glory.reactor.chat.infra.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.repository.UserRepository;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.UserNotify;

/**
 * ユーザリポジトリインメモリ実装.
 * @author gloryof
 *
 */
@Repository
public class UserRepositoryInMemoryImpl implements UserRepository {


    /**
     * ユーザMap.
     */
    private final Map<String, User> users = new LinkedHashMap<>();

    /**
     * ユーザ通知.
     */
    private final UserNotify notify;

    /**
     * コンストラクタ.
     * @param notify ユーザ通知
     */
    public UserRepositoryInMemoryImpl(final UserNotify notify) {

        this.notify = notify;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final User user) {

        users.put(user.getName().getValue(), user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {

        return users.entrySet().stream()
                .map(v -> v.getValue())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyToUsers() {

        notify.publish(findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Name name) {

        users.remove(name.getValue());
        notifyToUsers();
    }

}
