package ru.spbau.vishnyakov;

/**
 * For testing purposes.
 * @param <E> for test.
 */

public abstract class Animal<E> implements Eating {

    public static String  CATEGORY = "domestic";
    protected String name;

    protected abstract String getSound();

    Animal() {}

    Animal(String name){
        this.name = name;
    }
}