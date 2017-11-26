package jp.glory.reactor.chat.infra.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.repository.UserRepository;

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
     * {@inheritDoc}
     */
    @Override
    public void add(User user) {

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

}
