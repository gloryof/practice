package jp.glory.reactor.chat.domain.entity;

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
     * コンストラクタ.
     * @param name 名前
     * @param type タイプ
     */
    public User(final Name name) {

        this.name = name;
    }

    /**
     * 名前を取得する.
     * @return 名前
     */
    public Name getName() {

        return name;
    }

}
