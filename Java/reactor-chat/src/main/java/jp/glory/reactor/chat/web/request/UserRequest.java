package jp.glory.reactor.chat.web.request;

/**
 * ユーザリクエスト.
 * @author gloryof
 *
 */
public class UserRequest {

    /**
     * 名前.
     */
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
