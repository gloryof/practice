package jp.glory.java.playground.v22.apis.gatherers;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PocGatherer {
    public static void main(String[] args) {
        separate();
        window(IntStream.rangeClosed(1, 10).boxed(), 3);

        var values = IntStream.rangeClosed(1, 10).boxed()
                .map(v -> new Values(STR."test\{v}", v));
        separate();
        window(values, 3);

        List<Integer> empty = List.of();
        separate();
        window(empty.stream(), 3);
   }

    private static <T> void window(
        Stream<T> stream,
        int window
    ) {
        stream
            .gather(Gatherers.windowFixed(window))
            .forEach(System.out::println);
    }
    public static void separate() {
        System.out.println("############################");
    }

    record Values(String message, int value) {}
}
