package ru.spbau.mit.vishnyakov;

/**
 * Exception class for Maybe.
 */

public class MaybeException extends Exception {

    /**
     * Constructor.
     * @param s - represents error message.
     */

    public MaybeException(String s) {
        super(s);
    }
}
