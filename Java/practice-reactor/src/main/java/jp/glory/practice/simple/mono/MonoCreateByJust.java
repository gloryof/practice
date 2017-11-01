package jp.glory.practice.simple.mono;

import reactor.core.publisher.Mono;

public class MonoCreateByJust extends SimpleMonoBase {

    @Override
    public Mono<String> createMono() {

        return Mono.just("Test");
    }
}
