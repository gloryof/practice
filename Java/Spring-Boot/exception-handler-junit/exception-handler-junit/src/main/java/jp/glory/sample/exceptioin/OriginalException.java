package jp.glory.sample.exceptioin;

public class OriginalException extends RuntimeException {

    private final int id;

    public OriginalException(final int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }
}
