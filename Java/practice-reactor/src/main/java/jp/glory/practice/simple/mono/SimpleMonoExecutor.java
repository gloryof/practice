package jp.glory.practice.simple.mono;

import jp.glory.practice.simple.Tools;
import reactor.core.publisher.Mono;

public class SimpleMonoExecutor {

    public static void onlySubscribe(Mono<String> mono) {

        Tools.log("not yet started.");
        mono.subscribe(Tools::logWithSlow);
    }

    public static void subScribeTwice(Mono<String> mono) {

        Tools.log("start non param subscribe");
        mono.subscribe();

        Tools.log("start param subscribe");
        mono.subscribe(v -> Tools.logWithSlow("subscribe : " + v));
    }

    public static void setdDoOns(Mono<String> mono) {

        mono.doOnNext(v -> Tools.logWithSlow("doOnNext : " + v))
            .doOnSubscribe(v -> {
                Tools.logWithSlow("doOnSubscribe : ");
                v.request(1); // パラメータの意味がわかっていないので後で調査する
            })
            .doOnSuccess(v -> Tools.logWithSlow("doOnSuccess : " + v));
    }

    public static void filter(Mono<String> mono) {

        mono.filterWhen(v -> Mono.just(true)
                .doOnNext(v2 -> Tools.logWithSlow("doOnNext in filterWhen : "))
                .doOnSubscribe(v2 -> Tools.logWithSlow("doOnSubscribe in filterWhen : ")))
            .filter(v -> Tools.logWithSlow("filter : " + v))
            .subscribe(v -> Tools.logWithSlow("subscribe : " + v));

    }
}
