package jp.glory.practice.simple.flux;

import reactor.core.publisher.Flux;

public class FluxCreateByJust extends SimpleFluxBase {

    @Override
    public Flux<String> createFlux() {

        return Flux.just("test1", "test2", "test3");
    }

}
