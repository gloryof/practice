package jp.glory.practice.simple.flux;

import jp.glory.practice.simple.Tools;
import reactor.core.publisher.Flux;

public class FluxCreateByPush extends SimpleFluxBase {


    private final long waitSecond;

    public FluxCreateByPush(long waitSecond) {

        this.waitSecond = waitSecond;
    }

    @Override
    public Flux<String> createFlux() {

        return Flux.push(sink -> {

            for (int i = 1; i < 4; i++) {

                Tools.log("push flux : " + i);
                sink.next("Test" + i);
                Tools.slowExecution(waitSecond);
            }

            sink.complete();
        });
    }

}
