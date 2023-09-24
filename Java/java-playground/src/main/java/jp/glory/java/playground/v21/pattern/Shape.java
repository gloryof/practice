package jp.glory.java.playground.v21.pattern;

public sealed interface Shape permits Circle, Square {
    double calculateArea();
}
