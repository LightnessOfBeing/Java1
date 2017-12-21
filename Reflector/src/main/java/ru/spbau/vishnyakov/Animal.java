package ru.spbau.vishnyakov;

import java.util.ArrayList;

public abstract class Animal<E> implements Eating {

    public static String  CATEGORY = "domestic";
    private String name;

    protected abstract String getSound();

    Animal() {}
    //String ru.spba

    Animal(String name){
        this.name = name;
    }




    // constructor, standard getters and setters omitted
}