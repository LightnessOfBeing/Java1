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

        assertEquals(true, MaybeTest.readNumber(s).get().equals(1));
        assertEquals(true, MaybeTest.readNumber(s).get().equals(2));
        assertEquals(true, MaybeTest.readNumber(s).get().equals(3));
        assertEquals(true, MaybeTest.readNumber(s).get().equals(4));
        assertEquals(true, MaybeTest.readNumber(s).get().equals(5));
        assertEquals(true, MaybeTest.readNumber(s).get().equals(100));
        assertEquals(false, MaybeTest.readNumber(s).isPresent());
        assertEquals(false, MaybeTest.readNumber(s).isPresent());

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

        assertEquals(true, MaybeTest.readNumber(sOut).get().equals(1));
        assertEquals(true, MaybeTest.readNumber(sOut).get().equals(4));
        assertEquals(true, MaybeTest.readNumber(sOut).get().equals(9));
        assertEquals(true, MaybeTest.readNumber(sOut).get().equals(16));
        assertEquals(true, MaybeTest.readNumber(sOut).get().equals(25));
        assertEquals(true, MaybeTest.readNumber(sOut).get().equals(10000));
        assertEquals(false, MaybeTest.readNumber(sOut).isPresent());
        assertEquals(false, MaybeTest.readNumber(sOut).isPresent());

        sIn.close();
        sOut.close();
        output.delete();
    }

    @Test
    public void justValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);
        assertEquals(true, m.isPresent());
    }

    @Test
    public void nothingNoValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);
        assertEquals(true, m.isPresent());

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
            assertEquals(true, m.get().equals(10));
        } catch(MyException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void isPresentNoValue() throws Exception {
        Maybe<Integer> m = Maybe.nothing();

        assertEquals(false, m.isPresent());
    }

    @Test
    public void isPresentHasValue() throws Exception {
        Maybe<Integer> m = Maybe.just(10);

        assertEquals(true, m.isPresent());
    }

    @Test
    public void mapNonEmpty() throws Exception {
        Maybe<Integer> m = Maybe.just(2);
        try {
            assertEquals(true, m.map(x -> x * x).get().equals(4));
        } catch(MyException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testMapEmpty() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();

        assertEquals(false, maybe.map(x -> x * x).isPresent());
    }

}