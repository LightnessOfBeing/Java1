package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class MaybeTest {

    @Test
    public void readNumberTest() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";
        File file = new File(directory + "/file.in");
        try (Scanner s = new Scanner(file)) {
            assertEquals(1, Main.readNumber(s).get().intValue());
            assertEquals(2, Main.readNumber(s).get().intValue());
            assertEquals(3, Main.readNumber(s).get().intValue());
            assertEquals(4, Main.readNumber(s).get().intValue());
            assertEquals(5, Main.readNumber(s).get().intValue());
            assertEquals(100, Main.readNumber(s).get().intValue());
            assertFalse(Main.readNumber(s).isPresent());
            assertFalse(Main.readNumber(s).isPresent());
        }
    }

    @Test
    public void squareNumbersTest() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";
        String sourcePath = directory + "/file.in";
        String destinationPath = directory + "/file.out";

        ArrayList<String> actual = Main.readAndSquare(sourcePath, destinationPath);

        String[] answer = {"1", "4", "9", "16", "25", "10000", "null", "null"};
        assertArrayEquals(answer, actual.toArray());
    }

    @Test
    public void justValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);
        assertTrue(m.isPresent());
    }

    @Test
    public void nothingNoValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);
        assertTrue(m.isPresent());

    }

    @Test(expected = MyException.class)
    public void getNoValue() throws Exception {
        Maybe<Integer> m = Maybe.nothing();
        m.get();
    }

    @Test
    public void getHasValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);
        assertEquals(10, m.get().intValue());
    }

    @Test
    public void isPresentNoValue() throws Exception {
        Maybe<Integer> m = Maybe.nothing();
        assertFalse(m.isPresent());
    }

    @Test
    public void isPresentHasValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);
        assertTrue(m.isPresent());
    }

    @Test
    public void mapNonEmpty() throws Exception {
        Maybe<Integer> m = Maybe.just(2);
        assertEquals(4, m.map(x -> x * x).get().intValue());
    }

    @Test
    public void testMapEmpty() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        assertFalse(maybe.map(x -> x * x).isPresent());
    }
}