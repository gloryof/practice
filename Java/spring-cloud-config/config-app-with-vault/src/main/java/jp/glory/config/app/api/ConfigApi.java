package jp.glory.config.app.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RefreshScope
public class ConfigApi {

    @Value("${config.value:None}")
    private String configValue;

    @Value("${config.vault.value:None}")
    private String vaultValue;

    @GetMapping
    public ResponseEntity<Result> getMessage() {

        final Result result = new Result(configValue, vaultValue);

        return ResponseEntity.ok(result);
    }

    static class Result {
        public final String configValue;
        public final String vaultValue;

        Result(String configValue, String vaultValue) {

            this.configValue = configValue;
            this.vaultValue = vaultValue;
        }

    }
}
