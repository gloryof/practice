package jp.glory.analyze.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jp.glory.analyze.web.response.LogResponseResult;

/**
 * コンバージョンロガー.
 * @author gloryof
 *
 */
@Component
public class ConversionLogger {

	/**
	 * ロガー.
	 */
	private static final Logger logger = LoggerFactory.getLogger("conversionLog");

	/**
	 * ロギング.
	 * @param result リザルト
	 */
	public void log(final LogResponseResult result) {

		logger.info(result.name());
	}
}
