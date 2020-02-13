package jp.glory.config.app.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigApi {

    @Value("${config.value:None}")
    private String configValue;

    @GetMapping
    public ResponseEntity<Result> getMessage() {

        final Result result = new Result(configValue);

        return ResponseEntity.ok(result);
    }

    static class Result {
        public final String configValue;

        Result(String configValue) {

            this.configValue = configValue;
        }

    }
}
