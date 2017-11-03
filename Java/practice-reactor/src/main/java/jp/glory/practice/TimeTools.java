package jp.glory.practice;

public class TimeTools {

    public static void stay(long second) {

        try {

            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
