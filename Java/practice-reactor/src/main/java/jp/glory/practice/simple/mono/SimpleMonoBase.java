package jp.glory.practice.simple.mono;

import jp.glory.practice.simple.Tools;
import reactor.core.publisher.Mono;

abstract class SimpleMonoBase {

    public abstract Mono<String> createMono();

    public final void subscribe()  {

        final String methodName = "subscribe";

        Tools.methodStartLog(methodName);

        SimpleMonoExecutor.onlySubscribe(createMono());

        Tools.methodEndLog(methodName);
    }

    public final void doOn()  {

        final String methodName = "doOn";

        Tools.methodStartLog(methodName);

        Mono<String> mono = createMono();

        SimpleMonoExecutor.setdDoOns(mono);
        SimpleMonoExecutor.subScribeTwice(mono);

        Tools.methodEndLog(methodName);
    }

    public final void filter()  {

        final String methodName = "filter";

        Tools.methodStartLog(methodName);

        SimpleMonoExecutor.filter(createMono());

        Tools.methodEndLog(methodName);
    }
}
