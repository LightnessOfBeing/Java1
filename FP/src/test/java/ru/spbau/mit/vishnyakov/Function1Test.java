package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function1Test {
    @Test
    public void function1ApplyInteger() {
        Integer number = 7;
        Function1<Integer, Integer> function = (x) -> x * x;
        assertEquals(49, function.apply(number).intValue());
    }

    @Test
    public void function1ApplyInteger2() {
        Integer number = 3;
        Function1<Integer, Integer> function = (x) -> x * x * x;
        assertEquals(27, function.apply(number).intValue());
    }

    @Test
    public void function1ApplyString() {
        String s = "test";
        Function1<String, String> function = (x) -> x + "ing";
        assertEquals(0, function.apply(s).compareTo("testing"));
    }


    @Test
    public void function1ComposeInteger() {
        Integer number = 3;
        Function1<Integer, Integer> f = (x) -> x + 2;
        Function1<Integer, Integer> g = (x) -> x * x;
        Function1<Integer, Integer> gf = f.compose(g);
        Function1<Integer, Integer> fg =  g.compose(f);
        assertEquals(25, gf.apply(number).intValue());
        assertEquals(11, fg.apply(number).intValue());
    }

    @Test
    public void function1ComposeString() {
        String s = "test";
        Function1<String, String> f = (x) -> x + "ing ";
        Function1<String, String> g = (x) -> x + "er ";
        Function1<String, String> gf = f.compose(g);
        Function1<String, String> fg =  g.compose(f);
        assertEquals(0, gf.apply(s).compareTo("testing er "));
        assertEquals(0, fg.apply(s).compareTo("tester ing "));
    }
}