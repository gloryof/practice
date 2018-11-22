package jp.glory.analyze.web.response;

/**
 * ログのレスポンス.
 * @author gloryof
 *
 */
public class LogResponse {

	/**
	 * 結果.
	 */
	private final LogResponseResult result;

	/**
	 * コンストラクタ.
	 * @param result 結果
	 */
	public LogResponse(final LogResponseResult result) {

		this.result = result;
	}

	/**
	 * @return the result
	 */
	public LogResponseResult getResult() {
		return result;
	}

}
