package jp.glory.java.playground.v22.apis.gatherers;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class PocGatherer2 {
    public static void main(String[] args) {
        test();
        IntStream.rangeClosed(1, 10)
                .forEachOrdered(PocGatherer2::test2);
    }

    private static void test() {
        var before = OffsetDateTime.now();
        IntStream.range(0, 1000).boxed()
            .map(PocGatherer2::execute)
            .toList();
        var result =  ChronoUnit.MILLIS.between(before, OffsetDateTime.now());
        System.out.println(STR."test1: \{result}ms");
    }

    private static void test2(int concurrent) {
        var before = OffsetDateTime.now();
        IntStream.range(0, 1000).boxed()
            .gather(Gatherers.mapConcurrent(concurrent, PocGatherer2::execute))
            .toList();
        var result =  ChronoUnit.MILLIS.between(before, OffsetDateTime.now());
        System.out.println(STR."test2(\{concurrent}): \{result}ms");
    }

    private static int execute(int value) {
        try {
            Thread.sleep(10);
            return value + 1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
