package jp.glory.reactor.chat.web.response;

import jp.glory.reactor.chat.domain.entity.User;

/**
 * ユーザ詳細レスポンス.
 * @author gloryof
 *
 */
public class UserDetailResponse {

    /**
     * 名前.
     */
    private final String name;

    /**
     * コンストラクタ.
     * @param user
     */
    public UserDetailResponse(final User user) {

        this.name = user.getName().getValue();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}
