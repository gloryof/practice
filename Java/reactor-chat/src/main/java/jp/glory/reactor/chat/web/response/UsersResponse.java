package jp.glory.reactor.chat.web.response;

import java.util.List;

/**
 * ユーザレスポンス.
 * @author gloryof
 *
 */
public class UsersResponse {

    /**
     * ユーザリスト.
     */
    private List<UserDetailResponse> users;

    /**
     * ユーザ件数.
     */
    private int count;

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

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserDetailResponse> users) {
        this.users = users;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
