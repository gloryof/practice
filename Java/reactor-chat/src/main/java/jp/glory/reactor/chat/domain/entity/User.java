package jp.glory.reactor.chat.domain.entity;

import jp.glory.reactor.chat.domain.value.ChatType;
import jp.glory.reactor.chat.domain.value.Name;

/**
 * ユーザ.
 * @author gloryof
 *
 */
public class User {

    /**
     * 名前.
     */
    private final Name name;

    /**
     * タイプ.
     */
    private final ChatType type;

    /**
     * コンストラクタ.
     * @param name 名前
     * @param type タイプ
     */
    public User(final Name name, final ChatType type) {

        this.name = name;
        this.type = type;
    }

    /**
     * 名前を取得する.
     * @return 名前
     */
    public Name getName() {

        return name;
    }

    /**
     * タイプを取得する.
     * @return タイプ
     */
    public ChatType getType() {

        return type;
    }
}
