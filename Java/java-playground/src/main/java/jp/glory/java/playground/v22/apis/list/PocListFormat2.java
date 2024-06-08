package jp.glory.java.playground.v22.apis.list;

import java.text.ListFormat;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PocListFormat2 {

    public static void main(String[] args) {
        var twin = List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        var weeks = Arrays.stream(DayOfWeek.values()).toList();
        var values = List.of(twin, weeks);

        format1(values);

        separate();
        format2(Locale.JAPAN, values);

        separate();
        format2(Locale.US, values);

        separate();
        format3(Locale.JAPAN, values);

        separate();
        format3(Locale.US, values);

        separate();
        format4(Locale.JAPAN, values);

        separate();
        format4(Locale.US, values);
    }

    public static void separate() {
        System.out.println("############################");
    }
    public static void format1(List<List<DayOfWeek>> values) {
        values.forEach(v -> {
            var formated = ListFormat.getInstance().format(v);
            System.out.println(formated);
        });
    }

    public static void format2(
        Locale locale,
        List<List<DayOfWeek>> values
    ) {
        values.forEach(v -> {
            var formated = ListFormat.getInstance(
                locale,
                ListFormat.Type.OR,
                ListFormat.Style.FULL
            ).format(v);
            System.out.println(formated);
        });
    }

    public static void format3(
        Locale locale,
        List<List<DayOfWeek>> values
    ) {
        values.forEach(v -> {
            var formated = ListFormat.getInstance(
                locale,
                ListFormat.Type.STANDARD,
                ListFormat.Style.FULL
            ).format(v);
            System.out.println(formated);
        });
    }

    public static void format4(
        Locale locale,
        List<List<DayOfWeek>> values
    ) {
        values.forEach(v -> {
            var formated = ListFormat.getInstance(
                locale,
                ListFormat.Type.UNIT,
                ListFormat.Style.FULL
            ).format(v);
            System.out.println(formated);
        });
    }
}
