package ru.spbau.vishnyakov;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * For testing purposes.
 */

public class Goat extends Animal implements Locomotion {

    private ArrayList<Animal> array;

    @NotNull
    @Override
    protected java.lang.String getSound() {
        return "aaa";
    }

    @NotNull
    @Override
    public String getLocomotion() {
        return "walks";
    }

    @NotNull
    @Override
    public String eats() {
        return "grass";
    }

    public Goat(String name) {
        this.name = name;
    }

    @NotNull
    public ArrayList<Integer> getName() {
        return new ArrayList<>();
    }

    public void method(java.util.ArrayList<java.lang.Integer> arg1) {
    }

    public static class RollingInTheDeep {
        public static class Test {}
    }
}