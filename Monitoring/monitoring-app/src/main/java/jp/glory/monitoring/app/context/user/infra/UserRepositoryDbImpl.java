package jp.glory.monitoring.app.context.user.infra;

import java.util.List;
import java.util.stream.Collectors;

import jp.glory.monitoring.app.context.base.infra.DbRepository;
import jp.glory.monitoring.app.context.user.domain.entity.User;
import jp.glory.monitoring.app.context.user.domain.repository.UserRepository;
import jp.glory.monitoring.app.external.jdbc.user.UserTable;
import jp.glory.monitoring.app.external.jdbc.user.UserTableDao;

@DbRepository
public class UserRepositoryDbImpl implements UserRepository {

    private final UserTableDao dao;

    public UserRepositoryDbImpl(final UserTableDao dao) {

        this.dao = dao;
    }

    @Override
    public List<User> findAll() {

        return dao.findAll().stream()
                    .map(this::toUser)
                    .collect(Collectors.toList());
    }

    @Override
    public List<User> findInLimit() {

        return dao.findInLimit().stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    private User toUser(final UserTable table) {

        return new User(table.getId(), table.getName(), table.getAge());
    }
}
