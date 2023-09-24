package jp.glory.java.playground.v21.pattern;

sealed interface Shape permits Circle, Square {
    double calculateArea();
}
