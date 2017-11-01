package jp.glory.practice.simple.flux;

import jp.glory.practice.simple.Tools;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SimpleFluxExecutor {

    public static void onlySubscribe(Flux<String> flux) {

        Tools.log("not yet started.");
        flux.subscribe(Tools::logWithSlow);
    }

    public static void subScribeTwice(Flux<String> flux) {

        Tools.log("start non param subscribe");
        flux.subscribe();

        Tools.log("start param subscribe");
        flux.subscribe(v -> Tools.logWithSlow("subscribe : " + v));
    }

    public static void setdDoOns(Flux<String> flux) {

        flux.doOnNext(v -> Tools.logWithSlow("doOnNext : " + v))
            .doOnSubscribe(v -> {
                Tools.logWithSlow("doOnSubscribe : ");
                v.request(1); // パラメータの意味がわかっていないので後で調査する
            });
    }

    public static void filter(Flux<String> flux) {

        flux.filterWhen(v -> Mono.just(true)
                .doOnNext(v2 -> Tools.logWithSlow("doOnNext in filterWhen : "))
                .doOnSubscribe(v2 -> Tools.logWithSlow("doOnSubscribe in filterWhen : ")))
            .filter(v -> Tools.logWithSlow("filter : " + v))
            .subscribe(v -> Tools.logWithSlow("subscribe : " + v));
    }
}
