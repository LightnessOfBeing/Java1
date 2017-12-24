package ru.spbau.vishnyakov;

import java.util.ArrayList;

public abstract class Animal<E> implements Eating {

    public static String  CATEGORY = "domestic";
    protected String name;

    protected abstract String getSound();

    Animal() {}

    Animal(String name){
        this.name = name;
    }
}