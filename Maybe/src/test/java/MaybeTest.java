import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

public class MaybeTest {

    private static Maybe<Integer> readNumber(@NotNull Scanner s){
        String st = s.nextLine();
        Maybe<Integer> m;
        try {
            m = Maybe.just(parseInt(st));
        } catch (Exception ex) {
            m = Maybe.nothing();
        }
        return m;
    }

    @Test
    public void readNumberTest() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";
        File file = new File(directory + "/file.in");
        Scanner s = new Scanner(file);

        assertTrue(MaybeTest.readNumber(s).get().equals(1));
        assertTrue(MaybeTest.readNumber(s).get().equals(2));
        assertTrue(MaybeTest.readNumber(s).get().equals(3));
        assertTrue(MaybeTest.readNumber(s).get().equals(4));
        assertTrue(MaybeTest.readNumber(s).get().equals(5));
        assertTrue(MaybeTest.readNumber(s).get().equals(100));
        assertFalse(MaybeTest.readNumber(s).isPresent());
        assertFalse(MaybeTest.readNumber(s).isPresent());

        s.close();
    }

    @Test
    public void squareNumbersTest() throws Exception {
        String directory = System.getProperty("user.dir") + "/src/main/resources";
        File input = new File(directory + "/file.in");
        File output = new File(directory + "/file.out");
        Maybe<Integer> m;
        output.createNewFile();

        Scanner sIn = new Scanner(input);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/file.out"))) {
            for(int i = 0; i < 8; i++) {
                m = MaybeTest.readNumber(sIn);
                if (m.isPresent()) {
                    writer.write(m.map(x -> x * x).get().toString() + "\n");
                } else {
                    writer.write("null\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sIn.close();

        Scanner sOut = new Scanner(output);

        assertTrue(MaybeTest.readNumber(sOut).get().equals(1));
        assertTrue(MaybeTest.readNumber(sOut).get().equals(4));
        assertTrue(MaybeTest.readNumber(sOut).get().equals(9));
        assertTrue(MaybeTest.readNumber(sOut).get().equals(16));
        assertTrue(MaybeTest.readNumber(sOut).get().equals(25));
        assertTrue(MaybeTest.readNumber(sOut).get().equals(10000));
        assertFalse(MaybeTest.readNumber(sOut).isPresent());
        assertFalse(MaybeTest.readNumber(sOut).isPresent());

        sIn.close();
        sOut.close();
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
        try {
            assertTrue(m.get().equals(10));
        } catch(MyException e) {
            fail(e.getMessage());
        }
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
        try {
            assertTrue(m.map(x -> x * x).get().equals(4));
        } catch(MyException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testMapEmpty() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();

        assertFalse(maybe.map(x -> x * x).isPresent());
    }

}