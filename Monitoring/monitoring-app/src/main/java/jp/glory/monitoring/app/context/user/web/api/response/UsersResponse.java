package jp.glory.monitoring.app.context.user.web.api.response;

import java.util.List;
import java.util.stream.Collectors;

import jp.glory.monitoring.app.context.user.domain.entity.User;

public class UsersResponse {

    private final List<UserResponse> users;


    public UsersResponse(final List<User> users) {

        this.users = users.stream()
                            .map(UserResponse::new)
                            .collect(Collectors.toList());
    }


    /**
     * @return the users
     */
    public List<UserResponse> getUsers() {
        return users;
    }
}
