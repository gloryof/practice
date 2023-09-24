package jp.glory.java.playground.v21.string;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTemplateSampleTest {
    @Nested
    class TestStr {
        @Test
        public void test() {
            var sut = new StringTemplateSample();
            var testMessage = "test message";
            var clock = getClock();

            var actual = sut.testStr(clock, testMessage, 1, 2);

            assertEquals("Today is 2023-09-24.test message.Value is 3.", actual);
        }
    }

    @Nested
    class TestFmt {
        @Test
        public void test() {
            var sut = new StringTemplateSample();

            var actual = sut.testFmt(1, 2);

            assertEquals("00003", actual);
        }
    }

    @Nested
    class TestRaw {
        @Test
        public void test() {
            var sut = new StringTemplateSample();
            var testMessage = "test message";
            var clock = getClock();

            var actual = sut.testRaw(clock, testMessage, 1, 2);


            assertEquals("Today is 2023-09-24.test message.Value is 3.", actual.interpolate());
            assertArrayEquals(
                List.of(LocalDate.of(2023, 9, 24), "test message", 3).toArray(),
                actual.values().toArray()
            );
            assertArrayEquals(
                List.of("Today is ", ".", ".Value is ", ".").toArray(),
                actual.fragments().toArray()
            );
        }
    }

    private Clock getClock() {
        var offset = ZoneOffset.ofHours(9);
        return Clock.fixed(
            LocalDateTime.of(
                LocalDate.of(2023, 9, 24),
                LocalTime.now()
            ).toInstant(offset),
            offset
        );
    }
}
