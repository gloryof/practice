package jp.glory.practice.simple;

import java.time.LocalDateTime;

import jp.glory.practice.TimeTools;

public class Tools {

    private static final String SEP = "=============";

    public static boolean logWithSlow(String message) {

        slowExecution();

        log(message);

        return true;
    }

    public static void slowExecution() {

        TimeTools.stay(1);
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
