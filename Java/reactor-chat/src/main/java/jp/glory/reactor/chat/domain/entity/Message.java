package jp.glory.reactor.chat.domain.entity;

import jp.glory.reactor.chat.domain.value.Name;

/**
 * メッセージ.
 * @author gloryof
 *
 */
public class Message {

    /**
     * 発信者名.
     */
    private final Name name;

    /**
     * 内容.
     */
    private final String content;

    /**
     * コンストラクタ.
     * @param name 発信者名
     * @param content 内容
     */
    public Message(final Name name, final String content) {

        this.name = name;
        this.content = content;
    }

    /**
     * 末尾にプレフィックスを付与する.
     * @param prefix プレフィックス
     * @return 新しいメッセージ
     */
    public Message appendPrefix(final int prefix) {

        return new Message(name, content + "-" + prefix);
    }

    /**
     * 名前を取得する.
     * @return 名前
     */
    public Name getName() {

        return name;
    }

    /**
     * 内容を取得する.
     * @return 内容
     */
    public String getContent() {

        return content;
    }
}
