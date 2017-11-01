package jp.glory.practice.simple;

import java.time.LocalDateTime;

public class Tools {

    private static final String SEP = "=============";

    public static boolean logWithSlow(String message) {

        slowExecution();

        log(message);

        return true;
    }

    public static void slowExecution() {

        slowExecution(1);
    }


    public static void slowExecution(long second) {

        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void methodStartLog(String methodName) {

        log(SEP + " [" + methodName + "] is start " + SEP);
    }

    public static void methodEndLog(String methodName) {

        log(SEP + " [" + methodName + "] is e n d " + SEP);
    }

    public static void log(String message) {

        LocalDateTime time = LocalDateTime.now();

        System.out.println(time.toString() + " : " + message);
    }

}
