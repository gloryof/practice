package jp.glory.monitoring.app.context.user.web.api.response;

import jp.glory.monitoring.app.context.user.domain.entity.User;

public class UserResponse {

    private final long id;
    private final String name;
    private final int age;

    public UserResponse(final User user) {

        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }
}
