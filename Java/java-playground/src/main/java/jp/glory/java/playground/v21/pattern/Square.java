package jp.glory.java.playground.v21.pattern;

record Square(
    int x,
    int y
) implements Shape{
    @Override
    public double calculateArea() {
        return x * y;
    }

    public int sumOfInteriorAngles() {
        return 360;
    }
}
