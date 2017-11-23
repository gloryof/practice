package jp.glory.reactor.chat.domain.value;

/**
 * 名前.
 * @author gloryof
 *
 */
public class Name {

    /**
     * 値.
     */
    private final String value;

    /**
     * コンストラクタ.
     * @param value 値
     */
    public Name(final String value) {

        this.value = value;
    }

    /**
     * 値を取得する.
     * @return 値
     */
    public String getValue() {
        return value;
    }
}
