package ru.spbau.vishnyakov;

import org.jetbrains.annotations.NotNull;

/**
 * For testing purposes.
 * @param <E> for test.
 */

public abstract class Animal<E> implements Eating {

    @NotNull
    public static String  CATEGORY = "domestic";
    protected String name;

    @NotNull
    protected abstract String getSound();

    Animal() {}

    Animal(String name){
        this.name = name;
    }
}