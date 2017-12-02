package jp.glory.reactor.chat.web.request;

/**
 * メッセージリクエスト.
 * @author gloryof
 *
 */
public class MessageRequest {

    /**
     * メッセージ.
     */
    private String message = null;

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
