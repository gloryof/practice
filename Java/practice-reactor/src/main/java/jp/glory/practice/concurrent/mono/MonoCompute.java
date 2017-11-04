package jp.glory.practice.concurrent.mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jp.glory.practice.LogTools;
import jp.glory.practice.TimeTools;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MonoCompute {

    public static void main(String[] args) throws IOException {

        LogTools.log("start!");

        int result = Mono.just(createData())
                        .flatMapIterable(v -> v)
                        .parallel()
                        .runOn(Schedulers.parallel())
                        .map(v -> { 
                            LogTools.log("stay...");
                            TimeTools.stay(1);
                            return v * v;
                        })
                        .reduce((acc, v) -> acc + v)
                        .block();

        LogTools.log(String.valueOf(result));
        LogTools.log("finish");
    }

    private static List<Integer> createData() {

        LogTools.log("Search result...");
        TimeTools.stay(5);
        
        return IntStream.rangeClosed(1, 10).mapToObj(v -> v).collect(Collectors.toList());
    }
}
