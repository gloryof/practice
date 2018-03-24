package jp.glory.monitoring.app.context.user.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.glory.monitoring.app.context.base.web.WebApi;
import jp.glory.monitoring.app.context.user.usecase.SearchUser;
import jp.glory.monitoring.app.context.user.web.api.response.UsersResponse;

@WebApi
@RequestMapping("/users")
public class UserListWebApi {

    private final SearchUser searchUser;

    public UserListWebApi(final SearchUser searchUser) {

        this.searchUser = searchUser;
    }

    @GetMapping("/all")
    public UsersResponse findAll() {

        return new UsersResponse(searchUser.findAll());
    }

    @GetMapping("/limit")
    public UsersResponse findInLimit() {

        return new UsersResponse(searchUser.findInLimit());
    }
}
