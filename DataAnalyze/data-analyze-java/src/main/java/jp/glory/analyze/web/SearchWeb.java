package jp.glory.analyze.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.glory.analyze.logger.SearchLogger;
import jp.glory.analyze.web.search.SearchRequest;

@RestController
@RequestMapping("search")
public class SearchWeb {

	private final SearchLogger logger;

	public SearchWeb(final SearchLogger logger) {

		this.logger = logger;
	}


	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> search(@RequestBody final SearchRequest request) {

		logger.log(request);

		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
