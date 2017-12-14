package jp.glory.reactor.chat.domain.repository;

import java.util.List;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.value.Name;

/**
 * ユーザリポジトリ.
 * @author gloryof
 *
 */
public interface UserRepository {

    /**
     * ユーザを追加する.
     * @param user ユーザ
     */
    void add(final User user);

    /**
     * 全てのユーザを取得する.
     * @return
     */
    List<User> findAll();

    /**
     * 現在のユーザリストをユーザに通知する.
     */
    void notifyToUsers();

    /**
     * ユーザを削除する.
     * @param name 名前
     */
    void delete(Name name);
}
