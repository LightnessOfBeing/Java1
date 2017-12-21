package ru.spbau.vishnyakov;

import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class ReflectorTest {

    @Test
    public void test() {
        Class c = Goat.class;
        Reflector.printStructure(c);
    }

    @Test
    public void givenClass_whenRecognisesModifiers_thenCorrect() throws ClassNotFoundException {
        Class<?> goatClass = Class.forName("ru.spbau.vishnyakov.Goat");
        Class<?> animalClass = Class.forName("ru.spbau.vishnyakov.Animal");

        int goatMods = goatClass.getModifiers();
        int animalMods = animalClass.getModifiers();

        assertTrue(Modifier.isPublic(goatMods));
        assertTrue(Modifier.isAbstract(animalMods));
        assertTrue(Modifier.isPublic(animalMods));
    }

}