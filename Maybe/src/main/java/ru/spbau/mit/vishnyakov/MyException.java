package ru.spbau.mit.vishnyakov;

/**
 * Exception class for Maybe.
 */

public class MyException extends Exception {

    /**
     * Constructor.
     * @param s - represents error message.
     */

    public  MyException(String s) {
        super(s);
    }
}
