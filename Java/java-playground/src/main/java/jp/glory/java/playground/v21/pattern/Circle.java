package jp.glory.java.playground.v21.pattern;

public record Circle(
    int r
) implements Shape {
    @Override
    public double calculateArea() {
        return r * r * Math.PI;
    }

    public double circumference() {
        return 2 * r * Math.PI;
    }
}
