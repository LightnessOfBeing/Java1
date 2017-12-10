package ru.spbau.mit.vishnyakov;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class MaybeTest {

    @Nullable
    private static Maybe<Integer> readNumber(@NotNull Scanner s) {
        String st = s.nextLine();
        Maybe<Integer> m;
        try {
            m = Maybe.just(parseInt(st));
        } catch (NumberFormatException ex) {
            m =  Maybe.nothing();
        }
        return m;
    }

    @Test
    public void readNumberTest() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";
        File file = new File(directory + "/file.in");
        try (Scanner s = new Scanner(file)) {
            assertEquals(1, MaybeTest.readNumber(s).get().intValue());
            assertEquals(2, MaybeTest.readNumber(s).get().intValue());
            assertEquals(3, MaybeTest.readNumber(s).get().intValue());
            assertEquals(4, MaybeTest.readNumber(s).get().intValue());
            assertEquals(5, MaybeTest.readNumber(s).get().intValue());
            assertEquals(100, MaybeTest.readNumber(s).get().intValue());
            assertFalse(MaybeTest.readNumber(s).isPresent());
            assertFalse(MaybeTest.readNumber(s).isPresent());
        }
    }

    @Test
    public void squareNumbersTest() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";
        File input = new File(directory + "/file.in");
        File output = new File(directory + "/file.out");
        Maybe<Integer> m;
        output.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/file.out")); Scanner sIn = new Scanner(input)) {
            for(int i = 0; i < 8; i++) {
                m = MaybeTest.readNumber(sIn);
                if (m.isPresent()) {
                    writer.write(m.map(x -> x * x).get().toString() + "\n");
                }
                else {
                    writer.write("null\n");
                }
            }
        }

        try (Scanner sOut = new Scanner(output)) {
            assertEquals(1, MaybeTest.readNumber(sOut).get().intValue());
            assertEquals(4, MaybeTest.readNumber(sOut).get().intValue());
            assertEquals(9, MaybeTest.readNumber(sOut).get().intValue());
            assertEquals(16, MaybeTest.readNumber(sOut).get().intValue());
            assertEquals(25, MaybeTest.readNumber(sOut).get().intValue());
            assertEquals(10000, MaybeTest.readNumber(sOut).get().intValue());
            assertFalse(MaybeTest.readNumber(sOut).isPresent());
            assertFalse(MaybeTest.readNumber(sOut).isPresent());
        }

        output.delete();
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