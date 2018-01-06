package jp.glory.reactor.chat.web.response;

/**
 * メッセージレスポンス.
 * @author gloryof
 *
 */
public class MessageResponse {

    /**
     * 名前.
     */
    private String name;

    /**
     * タイプ.
     */
    private String content;

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

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}
