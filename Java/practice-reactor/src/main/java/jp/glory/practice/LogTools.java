package jp.glory.practice;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class LogTools {

    public static void log(String message) {

        String threadName = Thread.currentThread().getName();
        MessageFormat format = new MessageFormat("[{0}] {1} : {2}");
        System.out.println(format.format(new Object[] { threadName, LocalDateTime.now(), message }));

    }
}
