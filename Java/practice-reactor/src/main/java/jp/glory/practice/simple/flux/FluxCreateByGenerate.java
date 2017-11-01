package jp.glory.practice.simple.flux;

import jp.glory.practice.simple.Tools;
import reactor.core.publisher.Flux;

public class FluxCreateByGenerate extends SimpleFluxBase {

    @Override
    public Flux<String> createFlux() {

        return Flux.generate(
                () -> 1,
                (count, sink) -> {
                    if (3 < count) { sink.complete(); }
                    Tools.log("genrate : " + count);
                    sink.next("test" + count);
                    return count + 1;
                });
    }

}
