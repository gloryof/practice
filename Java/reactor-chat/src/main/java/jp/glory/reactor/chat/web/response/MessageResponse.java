package jp.glory.reactor.chat.web.response;

import jp.glory.reactor.chat.domain.entity.Message;

/**
 * メッセージレスポンス.
 * @author gloryof
 *
 */
public class MessageResponse {

    /**
     * 名前.
     */
    private final String name;

    /**
     * タイプ.
     */
    private final String content;

    /**
     * コンストラクタ.
     * @param message メッセージ
     */
    public MessageResponse(final Message message) {

        this.name = message.getName().getValue();
        this.content = message.getContent();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
