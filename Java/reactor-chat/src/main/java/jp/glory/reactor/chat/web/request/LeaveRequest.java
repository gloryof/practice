package jp.glory.reactor.chat.web.request;

/**
 * 退室リクエスト.
 * @author gloryof
 *
 */
public class LeaveRequest {

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
