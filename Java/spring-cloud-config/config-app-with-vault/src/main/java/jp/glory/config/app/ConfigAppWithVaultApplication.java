package jp.glory.config.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
public class ConfigAppWithVaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigAppWithVaultApplication.class, args);
	}

}
