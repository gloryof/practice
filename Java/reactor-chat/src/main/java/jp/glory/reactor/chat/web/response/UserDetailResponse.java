package jp.glory.reactor.chat.web.response;

/**
 * ユーザ詳細レスポンス.
 * @author gloryof
 *
 */
public class UserDetailResponse {

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
