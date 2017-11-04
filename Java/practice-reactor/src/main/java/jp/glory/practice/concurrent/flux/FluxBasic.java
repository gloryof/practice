package jp.glory.practice.concurrent.flux;

import java.io.IOException;

import jp.glory.practice.LogTools;
import jp.glory.practice.TimeTools;
import jp.glory.practice.concurrent.DataSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public class FluxBasic {

    public static void main(String[] args) throws IOException {

        DataSubscriber<Integer> subscriber = new DataSubscriber<>();

        ParallelFlux<String> reciever = FluxBasic.createReciever(subscriber);

        LogTools.log("Wait starting receiver.Please push enter.");
        System.in.read();
        reciever.subscribe(LogTools::log);
        LogTools.log("start reciver!");

        int maxCount = 10;
        Flux<Integer> sender = createSender(maxCount, subscriber);

        LogTools.log("Wait starting sender.Please push enter.");
        System.in.read();
        sender.subscribe();
        LogTools.log("start sender!");

        System.in.read();
        LogTools.log("finish");
    }

    private static ParallelFlux<String> createReciever(DataSubscriber<Integer> subscriber) {

        return Flux
                .push(sink -> {
                    subscriber.registerSubscribeFunc(v -> sink.next(v));
                    subscriber.registerCompleteFunc(sink::complete);
                })
                .parallel()
                .runOn(Schedulers.parallel())
                .map(FluxBasic::convertData);
    }

    private static Flux<Integer> createSender(int maxCount, DataSubscriber<Integer> subscriber) {

        return Flux
                .generate(
                    () -> 0,
                    (count, sink) -> {
                        if (maxCount < count) {
                            sink.complete();
                        }
                        sink.next(count);
                        return count + 1;
                    }
                )
                .map(v -> (Integer) v)
                .doOnNext(subscriber::subscribe)
                .doOnComplete(subscriber::compelete);
    }

    private static String convertData(Object value) {

        LogTools.log(value + " is mapping now...");
        TimeTools.stay(1);

        return "data-" + value;
    }
}
