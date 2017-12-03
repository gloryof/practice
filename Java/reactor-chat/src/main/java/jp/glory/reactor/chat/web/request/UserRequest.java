package jp.glory.reactor.chat.web.request;

import jp.glory.reactor.chat.domain.value.ChatType;

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
     * チャットタイプ.
     */
    private ChatType type;

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
     * @return the type
     */
    public ChatType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ChatType type) {
        this.type = type;
    }

    
}
