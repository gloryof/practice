package jp.glory.practice.concurrent.mono;

import java.io.IOException;

import jp.glory.practice.LogTools;
import jp.glory.practice.TimeTools;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MonoBasic {

    public static void main(String[] args) throws IOException {

        Mono.just("test")
            .repeat(5)
            .parallel()
            .runOn(Schedulers.parallel())
            .map(v -> { 
                LogTools.log("stay...");
                TimeTools.stay(1);
                return v + "-" + Thread.currentThread().getId();
            })
            .subscribe(LogTools::log);

        System.in.read();
        LogTools.log("finish");
    }
}
