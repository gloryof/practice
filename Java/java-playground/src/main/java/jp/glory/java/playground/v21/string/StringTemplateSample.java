package jp.glory.java.playground.v21.string;

import java.time.Clock;
import java.time.LocalDate;

import static java.lang.StringTemplate.RAW;
import static java.lang.StringTemplate.STR;
import static java.util.FormatProcessor.FMT;

public class StringTemplateSample {
    public String testStr(
        Clock clock,
        String message,
        int value1,
        int value2
    ) {
        return STR."Today is \{LocalDate.now(clock)}.\{message}.Value is \{value1 + value2}.";
    }

    public String testFmt(
        int value1,
        int value2
    ) {
        return FMT."%05d\{value1 + value2}";
    }


    public StringTemplate testRaw(
        Clock clock,
        String message,
        int value1,
        int value2
    ) {
        return RAW."Today is \{LocalDate.now(clock)}.\{message}.Value is \{value1 + value2}.";
    }
}
