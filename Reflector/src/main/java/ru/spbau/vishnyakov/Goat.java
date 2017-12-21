package ru.spbau.vishnyakov;

import java.util.ArrayList;

public final class Goat extends Animal implements Locomotion {

    @Override
    protected java.lang.String getSound() {
        return "bleat";
    }

    @Override
    public String getLocomotion() {
        return "walks";
    }

    @Override
    public String eats() {
        return "grass";
    }

    String name;

    public Goat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void lul(java.util.ArrayList<java.lang.Integer> arg1) {

    }
}