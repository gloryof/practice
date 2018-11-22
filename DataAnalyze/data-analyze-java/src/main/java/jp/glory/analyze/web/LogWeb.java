package jp.glory.analyze.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.glory.analyze.logger.ConversionLogger;
import jp.glory.analyze.web.response.LogResponse;
import jp.glory.analyze.web.response.LogResponseResult;

/**
 * ログを取得するコントローラ.
 * @author gloryof
 *
 */
@RestController
@RequestMapping("log")
public class LogWeb {

	/**
	 * コンバージョンログ.
	 */
	private final ConversionLogger conversionLogger;

	/**
	 * コンストラクタ.
	 * @param conversionLogger コンバージョンログ
	 */
	public LogWeb(final ConversionLogger conversionLogger) {

		this.conversionLogger = conversionLogger;
	}

	/**
	 * WebAPIの実行.<br>
	 * 受け取るパラメータは下記。<br>
	 * <ul>
	 * <li>result( {@link #parseResult(String)} )</li>
	 * <li>status( {@link #parseStatus(String)})</li>
	 * </ul>
	 * 各受け取る値はそれぞれのメソッドを参照。
	 * @param result 返される結果
	 * @param status ステータス
	 * @return
	 */
	@GetMapping
	public ResponseEntity<LogResponse> execute(
			@RequestParam(value = "result", required = false, defaultValue = "") final String result,
			@RequestParam(value = "status", required = false, defaultValue = "") final String status) {

		LogResponseResult parsedResult = parseResult(result);
		HttpStatus parsedStatus = parseStatus(status);

		conversionLogger.log(parsedResult);

		return new ResponseEntity<>(new LogResponse(parsedResult), parsedStatus);
	}

	/**
	 * resultの解析.<br>
	 * 値ごとに返すものは下記。<br>
	 * <dl>
	 * <dt>Great</dt>
	 * <dd>{@link LogResponseResult#Great}</dd>
	 * <dt>Bad</dt>
	 * <dd>{@link LogResponseResult#Bad}</dd>
	 * <dt>Error</dt>
	 * <dd>{@link IllegalArgumentException}をスローする</dd>
	 * <dt>上記以外</dt>
	 * <dd>{@link LogResponseResult#Soso}</dd>
	 * </dl>
	 * @param result 結果
	 * @return 解析結果
	 * @throws IllegalArgumentException Errorが渡された場合
	 */
	private LogResponseResult parseResult(final String result) throws IllegalArgumentException {

		switch(result) {
		case "Error":
			throw new IllegalArgumentException("result is Error");
		case "Great":
			return LogResponseResult.Great;
		case "Bad":
			return LogResponseResult.Bad;
		default:
			return LogResponseResult.Soso;
		}
	}

	/**
	 * statusの解析<br>
	 * 値ごとに返すものは下記。<br>
	 * <dl>
	 * <dt>400</dt>
	 * <dd>{@link HttpStatus#BAD_REQUEST}</dd>
	 * <dt>404</dt>
	 * <dd>{@link HttpStatus#NOT_FOUND}</dd>
	 * <dt>上記以外</dt>
	 * <dd>{@link HttpStatus#OK}</dd>
	 * </dl>
	 * @param status ステータス
	 * @return HTTPステータス
	 */
	private HttpStatus parseStatus(final String status) {

		switch (status) {
		case "400":
			return HttpStatus.BAD_REQUEST;
		case "404":
			return HttpStatus.NOT_FOUND;
		default: 
			return HttpStatus.OK;
		}
	}
}
