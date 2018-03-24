package jp.glory.monitoring.app.context.user.usecase;

import java.util.List;

import jp.glory.monitoring.app.context.base.usecase.UseCase;
import jp.glory.monitoring.app.context.user.domain.entity.User;
import jp.glory.monitoring.app.context.user.domain.repository.UserRepository;

@UseCase
public class SearchUser {

    private final UserRepository repository;

    public SearchUser(final UserRepository repository) {

        this.repository = repository;
    }

    public List<User> findAll() {

        return repository.findAll();
    }

    public List<User> findInLimit() {

        return repository.findInLimit();
    }
}
