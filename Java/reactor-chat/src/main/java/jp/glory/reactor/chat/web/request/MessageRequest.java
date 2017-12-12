package jp.glory.reactor.chat.web.request;

/**
 * メッセージリクエスト.
 * @author gloryof
 *
 */
public class MessageRequest {

    /**
     * ユーザ名.
     */
    private String username = null;

    /**
     * メッセージ.
     */
    private String message = null;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
