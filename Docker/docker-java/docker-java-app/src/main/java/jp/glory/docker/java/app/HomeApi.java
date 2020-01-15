package jp.glory.docker.java.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeApi {

    @GetMapping
    public ResponseEntity<String> getMessage() {

        return ResponseEntity.ok("OK!");
    }
}
