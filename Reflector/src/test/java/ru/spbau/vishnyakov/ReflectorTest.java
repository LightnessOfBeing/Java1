package ru.spbau.vishnyakov;

import org.junit.Test;

public class ReflectorTest {

    @Test
    public void Test() throws ClassNotFoundException {
        Reflector.printStructure(Goat.class);
    }
}