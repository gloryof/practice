package jp.glory.practice.simple.flux;

import jp.glory.practice.simple.Tools;
import reactor.core.publisher.Flux;

public abstract class SimpleFluxBase {

    public abstract Flux<String> createFlux();

    public final void subscribe()  {

        final String methodName = "subscribe";

        Tools.methodStartLog(methodName);

        SimpleFluxExecutor.onlySubscribe(createFlux());

        Tools.methodEndLog(methodName);
    }

    public final void doOn()  {

        final String methodName = "doOn";

        Tools.methodStartLog(methodName);

        Flux<String> Flux = createFlux();

        SimpleFluxExecutor.setdDoOns(Flux);
        SimpleFluxExecutor.subScribeTwice(Flux);

        Tools.methodEndLog(methodName);
    }

    public final void filter()  {

        final String methodName = "filter";

        Tools.methodStartLog(methodName);

        SimpleFluxExecutor.filter(createFlux());
        Tools.methodEndLog(methodName);
    }

}
