package jp.glory.java.playground.v21.pattern;

public class CalculateAreas {
    public String answer(Shape shape) {
        var area = shape.calculateArea();

        var info = switch (shape) {
            case Circle(int r) -> STR."[r : \{r}]";
            case Square(int x, int y) -> STR."[x : \{x}, y : \{y}]";
        };

        var calculated = switch (shape) {
            case Circle circle -> STR."Area is \{area}.Circumference is \{circle.circumference()}.";
            case Square square -> STR."Area is \{area}.Sum of interior angles is \{square.sumOfInteriorAngles()}.";
        };

        return STR."\{info}\{calculated}";
    }
}
