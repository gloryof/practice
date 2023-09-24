package jp.glory.java.playground.v21.pattern;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateAreasTest {
    @Nested
    class BySquare {
        @Test
        public void test() {
            var square = new Square(3, 5);
            var actual = new CalculateAreas().answer(square);

            var expected = "[x : 3, y : 5]Area is 15.0.Sum of interior angles is 360.";

            assertEquals(expected, actual);
        }
    }
    @Nested
    class ByCicle {
        @Test
        public void test() {
            var square = new Circle(4);
            var actual = new CalculateAreas().answer(square);

            var expected = "[r : 4]Area is 50.26548245743669.Circumference is 25.132741228718345.";

            assertEquals(expected, actual);
        }
    }
}
