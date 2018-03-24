package jp.glory.monitoring.app.context.user.domain.entity;

public class User {

    private final long id;

    private final String name;

    private final int age;

    public User(final long id, final String name, final int age) {

        this.id = id;
        this.name = name;
        this.age = age;
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
