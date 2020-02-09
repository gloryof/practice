package jp.glory.java.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeApi {

    @Value("${config.value:None}")
    private String configValue;

    @Value("${param.value:None}")
    private String paramValue;

    @Value("${secret.value:None}")
    private String secretValue;

    @GetMapping
    public ResponseEntity<Result> getMessage() {

        final Result result = new Result(configValue, paramValue, secretValue);

        return ResponseEntity.ok(result);
    }

    static class Result {
        public final String configValue;
        public final String paramValue;
        public final String secretValue;

        Result(String configValue, String paramValue, String secretValue) {

            this.configValue = configValue;
            this.paramValue = paramValue;
            this.secretValue = secretValue;
        }

    }
}
