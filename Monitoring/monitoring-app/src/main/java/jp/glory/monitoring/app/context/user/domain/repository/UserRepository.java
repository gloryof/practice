package jp.glory.monitoring.app.context.user.domain.repository;

import java.util.List;

import jp.glory.monitoring.app.context.user.domain.entity.User;

public interface UserRepository {

    List<User> findAll();

    List<User> findInLimit();
}
