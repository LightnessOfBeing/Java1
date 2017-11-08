package ru.spbau.mit.vishnyakov;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function2Test {
    @Test
    public void function2Apply() {
        Integer f = 5;
        Integer s = 10;
        Function2<Integer, Integer, Integer> function = (x, y) -> x + y;
        assertEquals(15, function.apply(f, s).intValue());
    }

    @Test
    public void function2ApplySecond() {
        Integer f = 5;
        Integer s = 10;
        Function2<Integer, Integer, Integer> function = (x, y) -> x - y;
        assertEquals(-5, function.apply(f, s).intValue());
    }

    @Test
    public void function2Compose() {
        Integer fir = 5;
        Integer s = 10;
        Function2<Integer, Integer, Integer> f = (x, y) -> x * y;
        Function1<Integer, Integer> g = (x) -> x + 1;
        Function2<Integer, Integer, Integer> composition = f.compose(g);
        assertEquals(51, composition.apply(fir, s).intValue());
    }

    @Test
    public void function1BindFirstArgument() {
        Integer num = 5;
        Function2<Integer, Integer, Integer> f = (x, y) -> x + y;
        Function1<Integer, Integer> g = f.bind1(10);
        assertEquals(15, g.apply(num).intValue());
    }


    @Test
    public void function1BindSecondArgument() {
        Integer num = 5;
        Function2<Integer, Integer, Integer> f = (x, y) -> x + y;
        Function1<Integer, Integer> g = f.bind2(10);
        assertEquals(15, g.apply(num).intValue());
    }

    @Test
    public void function1Currying() {
        Integer num = 5;
        Function2<Integer, Integer, Integer> f = (x, y) -> x + y;
        Function1<Integer, Function1<Integer, Integer>> oneArgument = f.curry();
        Function1<Integer, Integer> g = oneArgument.apply(10);
        assertEquals(15, g.apply(num).intValue());
    }
}