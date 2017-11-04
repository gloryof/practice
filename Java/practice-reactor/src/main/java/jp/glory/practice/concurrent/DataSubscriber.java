package jp.glory.practice.concurrent;

import java.util.function.Consumer;

public class DataSubscriber<T> {

    private Consumer<T> subscribeFunc = null;
    private Runnable completeFunc = null;

    public void subscribe(T data) {

        subscribeFunc.accept(data);
    }

    public void compelete() {

        completeFunc.run();
    }

    public void registerSubscribeFunc(Consumer<T> func) {

        this.subscribeFunc = func;
    }

    public void registerCompleteFunc(Runnable func) {

        completeFunc = func;
    }
}
