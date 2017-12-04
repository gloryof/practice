package jp.glory.reactor.chat.web.response;

import java.util.List;
import java.util.stream.Collectors;

import jp.glory.reactor.chat.domain.entity.User;

/**
 * コンストラクタ.
 * @author gloryof
 *
 */
public class UsersResponse {

    /**
     * ユーザリスト.
     */
    private final List<UserDetailResponse> users;

    /**
     * ユーザ件数.
     */
    private final int count;

    /**
     * コンストラクタ.
     * @param userList ユーザリスト
     */
    public UsersResponse(final List<User> userList) {

        this. users = userList.stream()
                .map(UserDetailResponse::new)
                .collect(Collectors.toList());
        this.count = users.size();
    }

    /**
     * @return the users
     */
    public List<UserDetailResponse> getUsers() {
        return users;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
}
