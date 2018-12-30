package jp.glory.analyze.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.glory.analyze.web.search.SearchRequest;

@Component
public class SearchLogger {

	/**
	 * ロガー.
	 */
	private static final Logger logger = LoggerFactory.getLogger("searchLog");

	/**
	 * ロギング処理.
	 * @param request リクエスト
	 */
	public void log(final SearchRequest request) {

		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

		try {

			logger.info(mapper.writeValueAsString(request));
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e);
		}
	}
}
